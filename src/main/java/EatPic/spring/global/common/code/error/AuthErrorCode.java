package EatPic.spring.global.common.code.error;

import EatPic.spring.global.common.BaseErrorCode;
import EatPic.spring.global.common.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

    // Auth 관련 에러
    AUTH_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_001", "토큰이 만료되었습니다."),
    AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_002", "토큰이 유효하지 않습니다."),
    INVALID_USERNAME_OR_PASSWORD(HttpStatus.UNAUTHORIZED, "AUTH_003", "이메일 또는 비밀번호가 유효하지 않습니다."),
    REQUEST_HEADER_INVALID(HttpStatus.BAD_REQUEST, "AUTH_004", "잘못된 요청 헤더입니다."),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "AUTH_005", "유저 인증에 실패했습니다."),
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, "AUTH_006", "해당 요청에 대한 권한이 없는 유저입니다."),
    LOGOUT_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_007", "로그아웃된 어세스 토큰 입니다"),
    TEMP_TOKEN_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "AUTH_014", "임시 토큰을 사용한 요청은 허용되지 않습니다."),
    INTERNAL_AUTH_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AUTH_009", "인증 처리 중 서버 에러가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder().isSuccess(false).code(code).message(message).build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .httpStatus(httpStatus)
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }
}
