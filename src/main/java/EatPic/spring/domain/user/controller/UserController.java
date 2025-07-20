package EatPic.spring.domain.user.controller;

import EatPic.spring.domain.user.dto.LoginRequestDTO;
import EatPic.spring.domain.user.dto.LoginResponseDTO;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.dto.SignupRequestDTO;
import EatPic.spring.domain.user.dto.SignupResponseDTO;
import EatPic.spring.domain.user.service.UserCommandServiceImpl;
import EatPic.spring.domain.user.service.UserService;
import EatPic.spring.global.common.BaseResponse;
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

    private final UserCommandServiceImpl userCommandService;


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

    // 필수 동의 약관 확인
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

//    @PostMapping("/login/email")
//    @Operation(summary = "이메일 로그인 요청")
//    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
//        LoginResponseDTO response = userService.loginuser(request);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/login/email")
    @Operation(summary = "유저 로그인 API",description = "유저가 로그인하는 API입니다.")
    public BaseResponse<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        return BaseResponse.onSuccess(userCommandService.loginUser(request));
    }

}