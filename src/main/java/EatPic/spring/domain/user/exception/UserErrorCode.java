package EatPic.spring.domain.user.exception;

import EatPic.spring.global.common.BaseErrorCode;
import EatPic.spring.global.common.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
  USER_NOT_FOUND(false, HttpStatus.NOT_FOUND, "USER_404", "존재하지 않는 사용자입니다.");


  private final boolean isSuccess;
  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  @Override
  public ErrorReasonDTO getReason() {
    return ErrorReasonDTO.builder()
        .isSuccess(isSuccess)
        .httpStatus(httpStatus)
        .code(code)
        .message(message)
        .build();
  }

  @Override
  public ErrorReasonDTO getReasonHttpStatus() {
    return getReason(); // 필요 시 httpStatus만 반환하도록 따로 구성 가능
  }

}
