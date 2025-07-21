package EatPic.spring.domain.card;

import EatPic.spring.global.common.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CardErrorCode implements BaseErrorCode {
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "CARD_001", "해당 카드는 존재하지 않는 카드입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
