package EatPic.spring.domain.user.controller;

import EatPic.spring.domain.user.converter.UserConverter;
import EatPic.spring.domain.user.dto.*;
import EatPic.spring.domain.user.dto.request.LoginRequestDTO;
import EatPic.spring.domain.user.dto.request.SignupRequestDTO;
import EatPic.spring.domain.user.dto.response.CheckEmailResponseDTO;
import EatPic.spring.domain.user.dto.response.CheckNicknameResponseDTO;
import EatPic.spring.domain.user.dto.response.LoginResponseDTO;
import EatPic.spring.domain.user.dto.response.SignupResponseDTO;
import EatPic.spring.domain.user.service.UserService;
import EatPic.spring.global.common.ApiResponse;
import EatPic.spring.global.common.code.status.ErrorStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RestControllerAdvice
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController{

    private final UserService userService;

    // 이메일 회원 가입 요청
    @PostMapping("/signup")
    @Operation(summary = "이메일 회원가입 요청")
    public ApiResponse<SignupResponseDTO> signup(@Valid @RequestBody SignupRequestDTO request) {
        return ApiResponse.onSuccess(userService.signup(request));
    }

    // 이메일 로그인 요청
    @PostMapping("/login/email")
    @Operation(summary = "이메일 로그인 요청")
    public ApiResponse<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        return ApiResponse.onSuccess(userService.loginUser(request));
    }

    // 유저 로그인 인증
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
    public ApiResponse<CheckEmailResponseDTO> checkEmail(@RequestParam String email) {
        boolean isDuplicate = userService.isEmailDuplicate(email);

        if(isDuplicate) {
            return ApiResponse.onFailure(
                    ErrorStatus.DUPLICATE_EMAIL.getCode(),
                    ErrorStatus.DUPLICATE_EMAIL.getMessage(),
                    UserConverter.toCheckEmailResponseDTO(email, false)
            );
        }
        return ApiResponse.onSuccess(UserConverter.toCheckEmailResponseDTO(email, true));
    }

    // 유저 아이디 중복 검사
    @GetMapping("/check-user-id")
    @Operation(summary = "유저 아이디 중복 검사")
    public ApiResponse<Map<String, Boolean>> checkUserId(@RequestParam String nameId) {
        boolean isDuplicate = userService.isnameIdDuplicate(nameId);
        return ApiResponse.onSuccess(Map.of("isDuplicate", isDuplicate));
    }

    // 닉네임 중복 검사
    @GetMapping("/check-nickname")
    @Operation(summary = "닉네임 중복 검사")
    public ApiResponse<CheckNicknameResponseDTO> checkNickname(@RequestParam String nickname) {
        boolean isDuplicate = userService.isNicknameDuplicate(nickname);

        if(isDuplicate) {
            return ApiResponse.onFailure(
                    ErrorStatus.DUPLICATE_NICKNAME.getCode(),
                    ErrorStatus.DUPLICATE_NICKNAME.getMessage(),
                    UserConverter.toCheckNicknameResponseDto(nickname, false)
            );
        }
        return ApiResponse.onSuccess(UserConverter.toCheckNicknameResponseDto(nickname, true));
    }
}
