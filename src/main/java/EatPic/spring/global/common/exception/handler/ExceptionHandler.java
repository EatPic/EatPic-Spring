package EatPic.spring.global.common.exception.handler;

import EatPic.spring.global.common.code.BaseErrorCode;
import EatPic.spring.global.common.exception.GeneralException;

public class ExceptionHandler extends GeneralException {
    public ExceptionHandler(BaseErrorCode code) {
        super(code);
    }
}
