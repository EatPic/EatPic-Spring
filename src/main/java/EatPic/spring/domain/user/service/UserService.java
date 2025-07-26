package EatPic.spring.domain.user.service;

import EatPic.spring.domain.user.dto.*;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    // UserCommandService
    SignupResponseDTO signup(SignupRequestDTO request);
    LoginResponseDTO loginUser(LoginRequestDTO request);
    UserResponseDTO.UserIconListResponseDto followingUserIconList(Long userId, int page, int size);
    UserResponseDTO.ProfileDto getMyIcon();

    // UserQueryService
    UserInfoDTO getUserInfo(HttpServletRequest request);

}
