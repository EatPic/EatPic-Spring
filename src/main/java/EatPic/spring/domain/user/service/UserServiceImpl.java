package EatPic.spring.domain.user.service;

import EatPic.spring.domain.card.dto.response.SearchResponseDTO;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.user.converter.UserConverter;
import EatPic.spring.domain.user.dto.*;
import EatPic.spring.domain.user.dto.request.LoginRequestDTO;
import EatPic.spring.domain.user.dto.request.SignupRequestDTO;
import EatPic.spring.domain.user.dto.request.UserRequest;
import EatPic.spring.domain.user.dto.response.LoginResponseDTO;
import EatPic.spring.domain.user.dto.response.RefreshTokenResponseDTO;
import EatPic.spring.domain.user.dto.response.SignupResponseDTO;
import EatPic.spring.domain.user.dto.response.UserResponseDTO;
import EatPic.spring.domain.user.entity.FollowStatus;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.entity.UserStatus;
import EatPic.spring.domain.user.mapping.UserBlock;
import EatPic.spring.domain.user.mapping.UserFollow;
import EatPic.spring.domain.user.repository.UserFollowRepository;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.domain.user.repository.UserBlockRepository;
import EatPic.spring.global.common.code.status.ErrorStatus;
import EatPic.spring.global.common.exception.GeneralException;
import EatPic.spring.global.common.exception.handler.ExceptionHandler;
import EatPic.spring.global.config.Properties.JwtProperties;
import EatPic.spring.global.config.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import EatPic.spring.global.aws.s3.*;

import java.util.*;

import static EatPic.spring.global.common.code.status.ErrorStatus.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;
    private final UserBlockRepository userBlockRepository;
    private final UserBadgeService userBadgeService;
    private final CardRepository cardRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;

    // s3 설정
    private final AmazonS3Manager s3Manager;

    // 회원가입
    @Transactional(readOnly = false)
    public SignupResponseDTO signup(SignupRequestDTO request) {
        // 이메일 중복 검사
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ExceptionHandler(ErrorStatus.DUPLICATE_EMAIL);
        }

        // 닉네임 중복 검사
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new ExceptionHandler(ErrorStatus.DUPLICATE_NICKNAME);
        }

        // 아이디 중복 검사
        if (userRepository.existsByNameId(request.getNameId())) {
            throw new ExceptionHandler(ErrorStatus.DUPLICATE_NAMEID);
        }

        // 저장
        User user = User.builder()
                .role(request.getRole())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nameId(request.getNameId())
                .nickname(request.getNickname())
                .marketingAgreed(request.getMarketingAgreed() != null && request.getMarketingAgreed())
                .notificationAgreed(request.getNotificationAgreed() != null && request.getNotificationAgreed())
                .userStatus(UserStatus.ACTIVE)
                .build();

        User savedUser = userRepository.save(user);

        // 뱃지 초기화
        userBadgeService.initializeUserBadges(savedUser);

        // DTO로 응답 생성
        return UserConverter.toSignupResponseDTO(savedUser);
    }

    // 로그인
    @Override
    @Transactional(readOnly = false)
    public LoginResponseDTO loginUser(LoginRequestDTO request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new ExceptionHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ExceptionHandler(ErrorStatus.INVALID_PASSWORD);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), null,
                //Collections.emptyList()
                Collections.singleton(() -> user.getRole().name())
        );

        String accessToken = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        // refreshToken -> DB에 저장
        user.updateRefreshToken(refreshToken);
        userRepository.save(user);

        return UserConverter.toLoginResultDTO(user, accessToken, refreshToken);
    }

    // JWT 사용자 정보 조회
    @Override
    @Transactional(readOnly = true)
    public UserInfoDTO getUserInfo(HttpServletRequest request) {
        Authentication authentication = jwtTokenProvider.extractAuthentication(request);
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus.MEMBER_NOT_FOUND));
        return UserConverter.toUserInfoDTO(user);
    }

    // refreshToken 재발급
    @Override
    public RefreshTokenResponseDTO reissueRefreshToken(HttpServletRequest request){
        // refresh token 추출
        String requestRefreshToken = jwtTokenProvider.resolveToken(request);

        // 토큰이 없으면 예외 발생
        if (!jwtTokenProvider.validateRefreshToken(requestRefreshToken)){
            throw new ExceptionHandler(ErrorStatus.INVALID_TOKEN);
        }

        // 토큰에서 이메일 추출, 사용자 정보 조회
        final String email = jwtTokenProvider.getSubject(requestRefreshToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // 저장된 refresh token과 요청으로 들어온 token의 일치 여부 확인
        final String storedRefreshToken = user.getRefreshToken();

        if (!requestRefreshToken.equals(user.getRefreshToken())) {
            throw new ExceptionHandler(ErrorStatus.INVALID_TOKEN);
        }

        // access token 재발급
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), null,
                //Collections.emptyList()
                Collections.singleton(() -> user.getRole().name())
        );

        String newAccessToken = jwtTokenProvider.generateToken(authentication);

        // refresh token 재발급 필요 여부 확인
        // access -> 30시간, refresh -> 5일
        // 재발급 임계일 설정 -> 3일
        boolean needReissueRefreshToken = expireWithinDays(requestRefreshToken, jwtProperties.getRefreshTokenReissueThresholdDays());
        String oldRefreshToken = user.getRefreshToken();

        if (needReissueRefreshToken) {
            oldRefreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());
            user.updateRefreshToken(oldRefreshToken);
            userRepository.save(user);
        }

        return RefreshTokenResponseDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(oldRefreshToken)
                .accessTokenExpiresIn(jwtProperties.getAccessTokenValidity())
                .build();
    }

    // refreshToken이 유효 기간 이내에 만료되는지 체크
    private boolean expireWithinDays(String jwt, int days) {
        long isRemained = jwtTokenProvider.getExpiredTime(jwt) - System.currentTimeMillis();
        long threshold = (long) days * 24L * 60L * 60L * 1000L;

        return isRemained <= threshold;
    }

    // 팔로잉한 유저의 프로필 아이콘 목록 조회
    @Override
    public UserResponseDTO.UserIconListResponseDto followingUserIconList(HttpServletRequest request,int page, int size) {
        User user = getLoginUser(request);
        Page<UserFollow> followingPage = userFollowRepository.findByUser(user, PageRequest.of(page, size));

        return UserConverter.toUserIconListResponseDto(followingPage);
    }

    // 내 프로필 아이콘 조회
    @Override
    public UserResponseDTO.ProfileDto getMyIcon(HttpServletRequest request) {
        User me = getLoginUser(request);
        return UserConverter.toProfileDto(me,true);
    }

    // 유저 차단
    @Transactional
    public UserResponseDTO.UserActionResponseDto blockUser(HttpServletRequest request, Long targetUserId) {
        User user = getLoginUser(request);
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new ExceptionHandler(USER_NOT_FOUND));

        if(userFollowRepository.existsByUserAndTargetUser(user,targetUser)){
            UserFollow userFollow = UserFollow.builder().user(user).targetUser(targetUser).build();
            userFollowRepository.delete(userFollow);
        }

        UserBlock userBlock = UserBlock.builder()
                .user(user)
                .blockedUser(targetUser)
                .build();

        userBlockRepository.save(userBlock);

        return UserConverter.toUserActionResponseDto(userBlock);
    }

    // 이메일 중복 검사
    public boolean isEmailDuplicate(String email){
        return userRepository.existsByEmail(email);
    }

    // 유저 아이디 중복 검사
    public boolean isnameIdDuplicate(String nameId){
        return userRepository.existsByNameId(nameId);
    }


    // 닉네임 중복 검사
    public boolean isNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public User getLoginUser(HttpServletRequest request) {
        Authentication authentication = jwtTokenProvider.extractAuthentication(request);
        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }

    @Override
    public UserResponseDTO.DetailProfileDto getProfile(HttpServletRequest request, Long userId) {
        User me = getLoginUser(request);

        User user = userRepository.findById(userId).orElseThrow(()-> new ExceptionHandler(USER_NOT_FOUND));
        Boolean isFollowing = userFollowRepository.existsByUserAndTargetUser(me, user);
        Long totalCard = cardRepository.countByUserIdAndIsDeletedFalseAndIsSharedTrue(userId);
        Long totalFollower = userFollowRepository.countUserFollowByTargetUser(user);
        Long totalFollowing = userFollowRepository.countUserFollowByUser(user);

        return UserConverter.toDetailProfileDto(user, isFollowing,totalCard,totalFollower,totalFollowing);
    }

    @Override
    @Transactional(readOnly = false)
    public UserResponseDTO.UserActionResponseDto unfollowUser(HttpServletRequest request, Long targetUserId) {
        User user = getLoginUser(request);
        User target = userRepository.findUserById(targetUserId);

        UserFollow prev = userFollowRepository.findByUserAndTargetUser(user,target);
        if(prev == null) {
            throw new ExceptionHandler(ErrorStatus.FOLLOW_NOT_EXISTS);
        }

        UserFollow follow = UserFollow.builder().user(user).targetUser(target).build();
        userFollowRepository.delete(follow);
        return UserConverter.toUserActionResponseDto(follow);
    }

    @Override
    @Transactional(readOnly = false)
    public UserResponseDTO.UserActionResponseDto followUser(HttpServletRequest request, Long targetUserId) {
        User user = getLoginUser(request);
        if(user.getId().equals(targetUserId)) {
            throw new ExceptionHandler(FOLLOW_FORBBIDEN);
        }

        User target = userRepository.findUserById(targetUserId);


        UserFollow prev = userFollowRepository.findByUserAndTargetUser(user,target);
        UserFollow follow = UserFollow.builder().user(user).targetUser(target).build();
        if(prev!=null && prev.getTargetUser().getId().equals(targetUserId)) {
            throw new ExceptionHandler(FOLLOW_ALREADY_EXISTS);
        }

        userFollowRepository.save(follow);
        return UserConverter.toUserActionResponseDto(follow);

    }

    // 유저 프로필 이미지 업데이트
    @Override
    @Transactional(readOnly = false)
    public UserResponseDTO.ProfileDto updateUserProfileImage(HttpServletRequest request, MultipartFile profileImage,User user) {

        String profileImageUrl = null;
        if (profileImage != null && !profileImage.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            String keyName = "userProfiles/" + uuid + "_" + profileImage.getOriginalFilename();

            try {
                profileImageUrl = s3Manager.uploadFile(keyName, profileImage);
            } catch (Exception e) {
                throw new GeneralException(ErrorStatus.FILE_UPLOAD_FAILED);
            }

            // 프로필 이미지 URL 업데이트
            user.setProfileImageUrl(profileImageUrl);

            // 유저 정보 DB 저장
            userRepository.save(user);
        }

        // 업데이트 결과를 DTO로 반환
        return UserResponseDTO.ProfileDto.builder()
                .userId(user.getId())
                .profileImageUrl(profileImageUrl) // 새 URL
                .build();
    }

    // 유저 소개 업데이트
    @Override
    @Transactional(readOnly = false)
    public UserResponseDTO.ProfileDto updateIntroduce(HttpServletRequest request, UserRequest.UpdateUserInroduceRequest introduce, User user) {

        user.setIntroduce(introduce.getIntroduce()); // introduce 부분만 변경

        userRepository.save(user);

        return UserResponseDTO.ProfileDto.builder()
                .userId(user.getId())
                .introduce(user.getIntroduce())
                .build();
    }

}