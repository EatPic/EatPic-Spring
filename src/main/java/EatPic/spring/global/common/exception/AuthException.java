package EatPic.spring.global.common.exception;

import EatPic.spring.global.common.BaseErrorCode;

public class AuthException extends GeneralException {
    public AuthException(BaseErrorCode errorCode) {super(errorCode);}
}