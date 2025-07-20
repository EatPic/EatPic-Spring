package EatPic.spring.global.common.exception.handler;

import EatPic.spring.global.common.BaseErrorCode;
import EatPic.spring.global.common.exception.GeneralException;

public class TempHandler extends GeneralException {
    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}