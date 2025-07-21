package EatPic.spring.domain.user.controller;

import EatPic.spring.domain.user.dto.UserResponseDTO;
import EatPic.spring.domain.user.service.UserCommandService;
import EatPic.spring.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class UserRestController {
    private final UserCommandService userCommandService;

    @Operation(
            summary = "커뮤니티 상단 팔로잉 유저 아이콘",
            description = "페이지는 1부터 시작하며 total은 전체 항목 수 입니다.")
    @GetMapping("/users")
    @Tag(name = "User", description = "사용자 관련 API")
    public BaseResponse<UserResponseDTO.UserIconListResponseDto> followingUsers(
                                      @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "15") int size) {
        //todo : userId -> 본인
        return BaseResponse.onSuccess(userCommandService.followingUserIconList(1L, page-1, size));
    }

    @Operation(
            summary = "커뮤니티 상단 팔로잉 유저 아이콘(나)")
    @GetMapping("/me")
    @Tag(name = "User", description = "사용자 관련 API")
    public BaseResponse<UserResponseDTO.ProfileDto> myIcon() {
        return BaseResponse.onSuccess(userCommandService.getMyIcon());
    }
}
