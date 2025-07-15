package EatPic.spring.domain.user.controller;

import EatPic.spring.domain.user.converter.UserConverter;
import EatPic.spring.domain.user.dto.UserResponseDTO;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.domain.user.service.UserCommandServiceImpl;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class UserRestController {
    private final UserCommandServiceImpl userCommandService;
    private final UserRepository userRepository;

    @Operation(
            summary = "커뮤니티 상단 나+팔로잉 유저 아이콘",
            description = "페이지는 1부터 시작하며, 1페이지의 첫 번째 데이터는 본인(사용자)입니다.")
    @GetMapping("/users")
    public BaseResponse<UserResponseDTO.UserIconListResponseDto> followingUsers(
                                      @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "15") int size) {
        Long userId = 1L;
        User me = userRepository.findUserById(userId);
        //todo: 커뮤니티 상단 프로필 맨 처음은 자신 -> 로그인 구현 후 수정 필요
        List<User> userList = new ArrayList<>();
        userList.add(me);
        // 이후 팔로잉한 유저
        List<User> followingList = userCommandService.followingUserList(userId);
        userList.addAll(followingList);

        int total = userList.size();

        //페이징
        int fromIndex = Math.max(0, (page - 1) * size);
        int toIndex = Math.min(fromIndex + size, total);
        List<UserResponseDTO.ProfileDto> pagedUserList = userList.subList(fromIndex,toIndex).stream().map(UserConverter::toProfileIconDto).toList();
        UserResponseDTO.UserIconListResponseDto result = UserConverter.toUserIconListResponseDto(total,page,size,pagedUserList);

        return BaseResponse.onSuccess(result);
    }
}
