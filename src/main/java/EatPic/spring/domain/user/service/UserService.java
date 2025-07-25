package EatPic.spring.domain.user.service;

import EatPic.spring.domain.user.converter.UserConverter;
import EatPic.spring.domain.user.dto.response.UserResponseDTO;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.dto.request.SignupRequestDTO;
import EatPic.spring.domain.user.entity.UserStatus;
import EatPic.spring.domain.user.mapping.UserBlock;
import EatPic.spring.domain.user.mapping.UserFollow;
import EatPic.spring.domain.user.repository.UserBlockRepository;
import EatPic.spring.domain.user.repository.UserFollowRepository;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.global.common.exception.handler.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static EatPic.spring.domain.user.exception.UserErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserBlockRepository userBlockRepository;
    private final UserFollowRepository userFollowRepository;

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
                .marketingAgreed(request.getMarketingAgreed() != null && request.getMarketingAgreed())
                .userStatus(UserStatus.ACTIVE)
                .build();

        return userRepository.save(user);
    }

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

    @Transactional
    public UserResponseDTO.UserIconListResponseDto followingUserIconList(Long userId, int page, int size) {
        User user = userRepository.findUserById(userId);
        Page<UserFollow> followingPage = userFollowRepository.findByUser(user, PageRequest.of(page, size));

        return UserConverter.toUserIconListResponseDto(followingPage);
    }

    @Transactional
    public UserResponseDTO.ProfileDto getMyIcon() {
        User me = userRepository.findUserById(1L);
        return UserConverter.toProfileDto(me,true);
    }

}