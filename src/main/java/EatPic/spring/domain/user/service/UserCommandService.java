package EatPic.spring.domain.user.service;

import EatPic.spring.domain.user.dto.UserResponseDTO;
import EatPic.spring.domain.user.entity.User;

public interface UserCommandService {
    UserResponseDTO.UserIconListResponseDto followingUserIconList(Long userId, int page, int size);
    UserResponseDTO.ProfileDto getMyIcon();
}
