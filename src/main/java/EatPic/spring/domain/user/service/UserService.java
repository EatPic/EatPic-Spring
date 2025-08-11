package EatPic.spring.domain.user.service;

import EatPic.spring.domain.user.dto.*;
import EatPic.spring.domain.user.dto.request.LoginRequestDTO;
import EatPic.spring.domain.user.dto.response.LoginResponseDTO;
import EatPic.spring.domain.user.dto.response.UserResponseDTO;
import EatPic.spring.domain.user.dto.request.SignupRequestDTO;
import EatPic.spring.domain.user.dto.response.SignupResponseDTO;
import EatPic.spring.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    // UserCommandService
    SignupResponseDTO signup(SignupRequestDTO request);
    LoginResponseDTO loginUser(LoginRequestDTO request);
    UserResponseDTO.UserIconListResponseDto followingUserIconList(HttpServletRequest request, int page, int size);
    UserResponseDTO.ProfileDto getMyIcon(HttpServletRequest request);
    UserResponseDTO.UserBlockResponseDto blockUser(HttpServletRequest request,Long targetUserId);
    boolean isEmailDuplicate(String email);
    boolean isnameIdDuplicate(String nameId);
    // UserQueryService
    UserInfoDTO getUserInfo(HttpServletRequest request);

    User getLoginUser(HttpServletRequest request);
}



