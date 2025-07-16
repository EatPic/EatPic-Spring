package EatPic.spring.domain.user.service;

import EatPic.spring.domain.user.dto.LoginRequestDTO;
import EatPic.spring.domain.user.dto.LoginResponseDTO;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.dto.SignupRequestDTO;
import EatPic.spring.domain.user.entity.UserStatus;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.global.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public User signup(SignupRequestDTO request) {
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

        return userRepository.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.generateToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateToken(user.getEmail());

        return new LoginResponseDTO(user.getId(), user.getEmail(), user.getNickname(), accessToken, refreshToken);
    }
}