package EatPic.spring.domain.user.controller;

import EatPic.spring.domain.user.dto.*;
import EatPic.spring.domain.user.service.UserService;
import EatPic.spring.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
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
public class UserController{

    private final UserService userService;

    // 회원 가입 요청
    @PostMapping("/signup")
    @Operation(summary = "이메일 회원가입 요청")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody SignupRequestDTO request) {
        return ResponseEntity.ok(userService.signup(request));
    }

    // 필수 동의 약관 확인 -> feature/5 Refacotor 하기
    @RestControllerAdvice
    public static class GlobalExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });

            return ResponseEntity.badRequest().body(errors);
        }
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
}

