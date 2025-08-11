package EatPic.spring.domain.user.controller;

import EatPic.spring.domain.user.dto.*;
import EatPic.spring.domain.user.dto.request.LoginRequestDTO;
import EatPic.spring.domain.user.dto.request.SignupRequestDTO;
import EatPic.spring.domain.user.dto.response.LoginResponseDTO;
import EatPic.spring.domain.user.dto.response.SignupResponseDTO;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.service.UserBadgeService;
import EatPic.spring.domain.user.service.UserService;
import EatPic.spring.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController{

    private final UserService userService;

    // 회원 가입 요청
    @PostMapping("/signup")
    @Operation(summary = "이메일 회원가입 요청")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody SignupRequestDTO request) {
        return ResponseEntity.ok(userService.signup(request));
    }

    @PostMapping("/login/email")
    @Operation(summary = "이메일 로그인 요청")
    public ApiResponse<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        return ApiResponse.onSuccess(userService.loginUser(request));
    }

    @GetMapping("/users")
    @Operation(summary = "유저 내 정보 조회 - 인증 필요",
            security = { @SecurityRequirement(name = "JWT TOKEN") }
    )
    public ApiResponse<UserInfoDTO> getMyInfo(HttpServletRequest request) {
        return ApiResponse.onSuccess(userService.getUserInfo(request));
    }

    // 이메일 중복 검사
    @GetMapping("/check-email")
    @Operation(summary = "이메일 중복 검사")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
        boolean isDuplicate = userService.isEmailDuplicate(email);
        return ResponseEntity.ok(Map.of("isDuplicate", isDuplicate));
    }
}

