package EatPic.spring.domain.user.controller;

import EatPic.spring.domain.user.dto.response.UserResponseDTO;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.service.UserService;
import EatPic.spring.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "사용자 관련 API")
public class UserRestController {
    private final UserService userService;

    @Operation(
            summary = "커뮤니티 상단 팔로잉 유저 아이콘",
            description = "페이지는 1부터 시작하며 total은 전체 항목 수 입니다.")
    @GetMapping("/icons/following")
    @Tag(name = "User", description = "사용자 관련 API")
    public ApiResponse<UserResponseDTO.UserIconListResponseDto> followingUsers(HttpServletRequest request,
                                                                               @RequestParam(defaultValue = "1") int page,
                                                                               @RequestParam(defaultValue = "15") int size) {
        //todo : userId -> 본인
        return ApiResponse.onSuccess(userService.followingUserIconList(request, page-1, size));
    }

    @Operation(
            summary = "커뮤니티 상단 팔로잉 유저 아이콘(나)")
    @GetMapping("/icons/me")
    @Tag(name = "User", description = "사용자 관련 API")
    public ApiResponse<UserResponseDTO.ProfileDto> myIcon(HttpServletRequest request) {
        return ApiResponse.onSuccess(userService.getMyIcon(request));
    }

    @Operation(summary = "해당 프로필의 유저를 차단 목록에 추가합니다.")
    @PostMapping("/{userId}/profile/block")
    @Tag(name = "User", description = "사용자 관련 API")
    public ApiResponse<UserResponseDTO.UserActionResponseDto> blockUser(HttpServletRequest request, @PathVariable Long userId) {
        return ApiResponse.onSuccess(userService.blockUser(request,userId));
    }

    @Operation(summary = "팔로잉")
    @PostMapping("/follow/{userId}")
    public ApiResponse<UserResponseDTO.UserActionResponseDto> followUser(HttpServletRequest request, @PathVariable Long userId) {
        return ApiResponse.onSuccess(userService.followUser(request,userId));
    }
    @Operation(summary = "팔로잉 취소")
    @DeleteMapping("/follow/{userId}")
    public ApiResponse<UserResponseDTO.UserActionResponseDto> unfollowUser(HttpServletRequest request, @PathVariable Long userId) {
        return ApiResponse.onSuccess(userService.unfollowUser(request,userId));
    }

    @Operation(summary = "프로필 이미지 수정", description = "마이페이지에서 본인의 프로필 이미지 수정 API")
    @PatchMapping("/setting/profile-image")
    @Tag(name = "User", description = "사용자 프로필 이미지 수정 API")
    public ApiResponse<UserResponseDTO.ProfileDto> updateUserProfileImage(HttpServletRequest request) {
        return ApiResponse.onSuccess(userService)
    }

}
