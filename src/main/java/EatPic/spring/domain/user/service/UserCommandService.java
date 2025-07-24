package EatPic.spring.domain.user.service;

import EatPic.spring.domain.user.dto.response.UserResponseDTO;

public interface UserCommandService {
    UserResponseDTO.UserIconListResponseDto followingUserIconList(Long userId, int page, int size);
    UserResponseDTO.ProfileDto getMyIcon();
}
