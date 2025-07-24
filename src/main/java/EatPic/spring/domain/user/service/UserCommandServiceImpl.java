package EatPic.spring.domain.user.service;

import EatPic.spring.domain.user.converter.UserConverter;
import EatPic.spring.domain.user.dto.response.UserResponseDTO;
import EatPic.spring.domain.user.mapping.UserFollow;
import EatPic.spring.domain.user.repository.UserFollowRepository;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;

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
}
