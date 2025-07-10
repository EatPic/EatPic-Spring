package EatPic.spring.domain.user.service;

import EatPic.spring.domain.user.User;
import EatPic.spring.domain.user.dto.SignupRequestDTO;
import EatPic.spring.domain.user.enums.UserStatus;
import EatPic.spring.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
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
                .marketingAgree(request.getMarketingAgree() != null && request.getMarketingAgree())
                .userStatus(UserStatus.ACTIVE)
                .build();

        return userRepository.save(user);
    }
}