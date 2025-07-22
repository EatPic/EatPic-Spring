package EatPic.spring.global.common;

import EatPic.spring.global.common.code.ErrorReasonDTO;

public interface BaseErrorCode {
    ErrorReasonDTO getReason();
    ErrorReasonDTO getReasonHttpStatus();
}
