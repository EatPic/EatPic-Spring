package EatPic.spring.domain.user.service;

import EatPic.spring.domain.user.converter.UserConverter;
import EatPic.spring.domain.user.dto.*;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.entity.UserStatus;
import EatPic.spring.domain.user.mapping.UserFollow;
import EatPic.spring.domain.user.repository.UserFollowRepository;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.global.common.code.status.ErrorStatus;
import EatPic.spring.global.common.exception.handler.ExceptionHandler;
import EatPic.spring.global.config.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nameId(request.getNameId())
                .nickname(request.getNickname())
                .marketingAgreed(request.getMarketingAgreed() != null && request.getMarketingAgreed())
                .userStatus(UserStatus.ACTIVE)
                .build();

        User savedUser = userRepository.save(user);

        // DTO로 응답 생성
        return SignupResponseDTO.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .nameId(savedUser.getNameId())
                .nickname(savedUser.getNickname())
                .marketingAgreed(savedUser.getMarketingAgreed())
                .message("회원가입이 완료되었습니다.")
                .build();
    }

    @Override
    public UserResponseDTO.UserIconListResponseDto followingUserIconList(Long userId, int page, int size) {
        User user = userRepository.findUserById(userId);
        Page<UserFollow> followingPage = userFollowRepository.findByUser(user, PageRequest.of(page, size));

        return UserConverter.toUserIconListResponseDto(followingPage);
    }

    @Override
    public UserResponseDTO.ProfileDto getMyIcon() {
        User me = userRepository.findUserById(1L);
        return UserConverter.toProfileDto(me,true);
    }

    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new ExceptionHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ExceptionHandler(ErrorStatus.INVALID_PASSWORD);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), null,
                Collections.emptyList()
                //Collections.singleton(() -> user.getRole().name())
        );

        String accessToken = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        return UserConverter.toLoginResultDTO(
                user.getId(),
                accessToken,
                refreshToken
        );
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfoDTO getUserInfo(HttpServletRequest request) {
        Authentication authentication = jwtTokenProvider.extractAuthentication(request);
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus.MEMBER_NOT_FOUND));
        return UserConverter.toUserInfoDTO(user);
    }
}