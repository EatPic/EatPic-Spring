package EatPic.spring.domain.community.controller;

import EatPic.spring.domain.community.converter.UserConverter;
import EatPic.spring.domain.community.dto.UserResponseDTO;
import EatPic.spring.domain.community.service.UserCommandService;
import EatPic.spring.domain.community.service.UserCommandServiceImpl;
import EatPic.spring.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class UserRestController {
    private final UserCommandServiceImpl userCommandService;

    @PostMapping("/users")
    public List<UserResponseDTO.ProfileIconDto> followingUsers(@RequestBody Long userId) {
        List<User> followingList = userCommandService.followingUser(userId);
        return followingList.stream()
                .map(UserConverter::toProfileIconDto)
                .collect(Collectors.toList());
    }
}
