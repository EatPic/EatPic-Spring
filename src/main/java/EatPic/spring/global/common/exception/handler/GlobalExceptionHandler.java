package EatPic.spring.global.common.exception.handler;

import EatPic.spring.global.common.BaseResponse;
import EatPic.spring.global.common.BaseErrorCode;
import EatPic.spring.global.common.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<BaseResponse<Object>> handleGeneralException(GeneralException ex) {
        BaseErrorCode errorCode = ex.getCode();
        log.error("[GeneralException] #y: {}", errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getReasonHttpStatus().getHttpStatus())
                .body(BaseResponse.onFailure(
                        errorCode.getReasonHttpStatus().getCode(),
                        errorCode.getReasonHttpStatus().getMessage(),
                        null
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.warn("[ValidationException] {}", message);
        return ResponseEntity.badRequest().body(
                BaseResponse.onFailure("VALIDATION_ERROR", message, null)
        );
    }
}