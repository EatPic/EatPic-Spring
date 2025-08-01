package EatPic.spring.domain.user.controller;

import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.dto.request.SignupRequestDTO;
import EatPic.spring.domain.user.dto.response.SignupResponseDTO;
import EatPic.spring.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 가입 요청
    @PostMapping("/signup")
    @Operation(summary = "이메일 회원가입 요청")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody SignupRequestDTO request) {

        User savedUser = userService.signup(request);

        SignupResponseDTO response = SignupResponseDTO.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .nameId(savedUser.getNameId())
                .nickname(savedUser.getNickname())
                .marketingAgreed(savedUser.getMarketingAgreed())
                .message("회원가입이 완료되었습니다.")
                .build();

        return ResponseEntity.ok(response);
    }

}
