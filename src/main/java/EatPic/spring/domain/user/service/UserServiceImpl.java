package EatPic.spring.domain.user.service;

import EatPic.spring.domain.user.converter.UserConverter;
import EatPic.spring.domain.user.dto.*;
import EatPic.spring.domain.user.dto.request.LoginRequestDTO;
import EatPic.spring.domain.user.dto.request.SignupRequestDTO;
import EatPic.spring.domain.user.dto.response.LoginResponseDTO;
import EatPic.spring.domain.user.dto.response.SignupResponseDTO;
import EatPic.spring.domain.user.dto.response.UserResponseDTO;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.entity.UserStatus;
import EatPic.spring.domain.user.mapping.UserBlock;
import EatPic.spring.domain.user.mapping.UserFollow;
import EatPic.spring.domain.user.repository.UserFollowRepository;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.domain.user.repository.UserBlockRepository;
import EatPic.spring.global.common.code.status.ErrorStatus;
import EatPic.spring.global.common.exception.handler.ExceptionHandler;
import EatPic.spring.global.config.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static EatPic.spring.global.common.code.status.ErrorStatus.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;
    private final UserBlockRepository userBlockRepository;
    private final UserBadgeService userBadgeService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    //private final UserDetailsService userDetailsService;

    // 회원가입
    public SignupResponseDTO signup(SignupRequestDTO request) {
        // 이메일 중복 검사
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 닉네임 중복 검사
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // 아이디 중복 검사
        if (userRepository.existsByNameId(request.getNameId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
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
        return SignupResponseDTO.builder()
                .role(request.getRole())
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .nameId(savedUser.getNameId())
                .nickname(savedUser.getNickname())
                .marketingAgreed(savedUser.getMarketingAgreed())
                .notificationAgreed(savedUser.getNotificationAgreed())
                .message("회원가입이 완료되었습니다.")
                .build();
    }

    // 로그인
    @Override
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

    // 팔로잉한 유저의 프로필 아이콘 목록 조회
    @Override
    public UserResponseDTO.UserIconListResponseDto followingUserIconList(Long userId, int page, int size) {
        User user = userRepository.findUserById(userId);
        Page<UserFollow> followingPage = userFollowRepository.findByUser(user, PageRequest.of(page, size));

        return UserConverter.toUserIconListResponseDto(followingPage);
    }

    // 내 프로필 아이콘 조회
    @Override
    public UserResponseDTO.ProfileDto getMyIcon() {
        User me = userRepository.findUserById(1L);
        return UserConverter.toProfileDto(me,true);
    }

    // 유저 차단
    @Transactional
    public UserResponseDTO.UserBlockResponseDto blockUser(Long targetUserId) {
        User user = userRepository.findUserById(1L);
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new ExceptionHandler(USER_NOT_FOUND));

        UserBlock userBlock = UserBlock.builder()
                .user(user)
                .blockedUser(targetUser)
                .build();

        userBlockRepository.save(userBlock);

        return UserConverter.toUserBlockResponseDto(userBlock);
    }
}