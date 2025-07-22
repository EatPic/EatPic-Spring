package EatPic.spring.domain.user.service;

import EatPic.spring.domain.user.dto.LoginResponseDTO;
import EatPic.spring.domain.user.dto.LoginRequestDTO;
import EatPic.spring.domain.user.dto.UserResponseDTO;


public interface UserCommandService {
    LoginResponseDTO loginUser(LoginRequestDTO request);
    UserResponseDTO.UserIconListResponseDto followingUserIconList(Long userId, int page, int size);
    UserResponseDTO.ProfileDto getMyIcon();
}
