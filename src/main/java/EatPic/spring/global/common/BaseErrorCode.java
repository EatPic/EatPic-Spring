package EatPic.spring.global.common;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();

    default ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .isSuccess(false)
                .code(getCode())
                .message(getMessage())
                .build();
    }

    default ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .httpStatus(getHttpStatus())
                .isSuccess(false)
                .code(getCode())
                .message(getMessage())
                .build();
    }
}
