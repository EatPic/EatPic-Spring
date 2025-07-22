package EatPic.spring.domain.user.exception;

import EatPic.spring.global.common.code.BaseErrorCode;
import EatPic.spring.global.common.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
  USER_NOT_FOUND(false, HttpStatus.NOT_FOUND, "USER_404", "존재하지 않는 사용자입니다."),

  // User Error
  MEMBER_NOT_FOUND(false,HttpStatus.BAD_REQUEST, "USER_404", "사용자가 없습니다."),
  INVALID_PASSWORD(false,HttpStatus.BAD_REQUEST, "USER_401", "비밀번호가 불일치합니다."),
  INVALID_TOKEN(false,HttpStatus.BAD_REQUEST, "USER_403", "유효하지 않은 토큰입니다."),
  DUPLICATE_JOIN_REQUEST(false,HttpStatus.BAD_REQUEST, "USER_400", "해당 이메일로 이미 가입된 사용자가 존재합니다.");

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
