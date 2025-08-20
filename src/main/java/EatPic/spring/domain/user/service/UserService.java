package EatPic.spring.domain.user.service;

import EatPic.spring.domain.card.dto.response.SearchResponseDTO;
import EatPic.spring.domain.user.dto.*;
import EatPic.spring.domain.user.dto.request.LoginRequestDTO;
import EatPic.spring.domain.user.dto.request.UserRequest;
import EatPic.spring.domain.user.dto.response.LoginResponseDTO;
import EatPic.spring.domain.user.dto.response.RefreshTokenResponseDTO;
import EatPic.spring.domain.user.dto.response.UserResponseDTO;
import EatPic.spring.domain.user.dto.request.SignupRequestDTO;
import EatPic.spring.domain.user.dto.response.SignupResponseDTO;
import EatPic.spring.domain.user.entity.FollowStatus;
import EatPic.spring.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    // UserCommandService
    SignupResponseDTO signup(SignupRequestDTO request);
    LoginResponseDTO loginUser(LoginRequestDTO request);
    UserResponseDTO.UserIconListResponseDto followingUserIconList(HttpServletRequest request, int page, int size);
    UserResponseDTO.ProfileDto getMyIcon(HttpServletRequest request);
    UserResponseDTO.UserActionResponseDto blockUser(HttpServletRequest request, Long targetUserId);
    boolean isEmailDuplicate(String email);
    boolean isnameIdDuplicate(String nameId);
    boolean isNicknameDuplicate(String nickname);
    RefreshTokenResponseDTO reissueRefreshToken(HttpServletRequest request);

    // UserQueryService
    UserInfoDTO getUserInfo(HttpServletRequest request);
    UserResponseDTO.UserActionResponseDto followUser(HttpServletRequest request, Long targetUserId);
    UserResponseDTO.UserActionResponseDto unfollowUser(HttpServletRequest request, Long targetUserId);
    User getLoginUser(HttpServletRequest request);
    UserResponseDTO.DetailProfileDto getProfile(HttpServletRequest request,Long userId);

    // 마이페이지 업데이트 관련 서비스
    UserResponseDTO.ProfileDto updateUserProfileImage(HttpServletRequest request, MultipartFile profileImage, User user);
    UserResponseDTO.ProfileDto updateIntroduce(HttpServletRequest request, UserRequest.UpdateUserInroduceRequest introduce, User user);
}



