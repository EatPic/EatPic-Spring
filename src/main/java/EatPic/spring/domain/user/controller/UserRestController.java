package EatPic.spring.domain.user.controller;

import EatPic.spring.domain.user.dto.response.UserResponseDTO;
import EatPic.spring.domain.user.service.UserService;
import EatPic.spring.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class UserRestController {
    private final UserService userService;

    @Operation(
            summary = "커뮤니티 상단 팔로잉 유저 아이콘",
            description = "페이지는 1부터 시작하며 total은 전체 항목 수 입니다.")
    @GetMapping("/users")
    @Tag(name = "User", description = "사용자 관련 API")
    public ApiResponse<UserResponseDTO.UserIconListResponseDto> followingUsersIcon(
                                      @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "15") int size) {
        //todo : userId -> 본인
        return ApiResponse.onSuccess(userService.followingUserIconList(1L, page-1, size));
    }

    @Operation(
            summary = "커뮤니티 상단 팔로잉 유저 아이콘(나)")
    @GetMapping("/me")
    @Tag(name = "User", description = "사용자 관련 API")
    public ApiResponse<UserResponseDTO.ProfileIconDto> myIcon() {
        return ApiResponse.onSuccess(userService.getMyIcon());
    }

    @Operation(summary = "해당 프로필의 유저를 차단 목록에 추가합니다.")
    @PostMapping("/{userId}/profile/block")
    @Tag(name = "User", description = "사용자 관련 API")
    public ApiResponse<UserResponseDTO.UserBlockResponseDto> blockUser(@PathVariable Long userId) {
        return ApiResponse.onSuccess(userService.blockUser(userId));
    }

    @Operation(summary = "유저 프로필 조회")
    @PostMapping("/{userId}/profile")
    @Tag(name = "User", description = "사용자 관련 API")
    public ApiResponse<UserResponseDTO.DetailProfileDto> getProfile(@PathVariable Long userId) {
        return ApiResponse.onSuccess(userService.getProfile(userId));
    }

//    @Operation(summary = "유저 프로필 조회(이미지)")
//    @PostMapping("/{userId}/profile/cards")
//    @Tag(name = "User", description = "사용자 관련 API")
//    public ApiResponse<UserResponseDTO.DetailProfileDto> getProfile(@PathVariable Long userId) {
//        return ApiResponse.onSuccess(userService.getProfile(userId));
//    }


}
