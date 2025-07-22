package EatPic.spring.domain.user.exception.handler;

import EatPic.spring.global.common.code.BaseErrorCode;
import EatPic.spring.global.common.exception.GeneralException;

public class UserHandler extends GeneralException {
    public UserHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
