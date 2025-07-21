package EatPic.spring.domain.comment;

import EatPic.spring.global.common.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentErrorCode implements BaseErrorCode {
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_001", "해당 댓글은 존재하지 않는 댓글입니다."),
    CURSOR_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMMENT_002", "유효하지 않은 커서입니다.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}