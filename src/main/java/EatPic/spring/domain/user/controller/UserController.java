package EatPic.spring.domain.user.controller;

import EatPic.spring.domain.user.User;
import EatPic.spring.domain.user.dto.request.SignupRequest;
import EatPic.spring.domain.user.dto.request.SignupResponse;
import EatPic.spring.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {

        User savedUser = userService.signup(request);

        SignupResponse response = SignupResponse.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .nameId(savedUser.getNameId())
                .nickname(savedUser.getNickname())
                .marketingAgree(savedUser.getMarketingAgree())
                .message("회원가입이 완료되었습니다.")
                .build();

        return ResponseEntity.ok(response);
    }
}
