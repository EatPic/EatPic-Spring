package EatPic.spring.global.common.code.status;

import EatPic.spring.global.common.code.BaseErrorCode;
import EatPic.spring.global.common.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 기본 에러
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON_401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_403", "금지된 요청입니다."),
    _NO_RESULTS_FOUND(HttpStatus.NOT_FOUND, "COMMON_404", "검색 결과가 없습니다."),
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 에러. 관리자에게 문의 바랍니다."),

    MULTIPLE_FIELD_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "COMMON_001", "입력된 정보에 오류가 있습니다. 필드별 오류 메시지를 참조하세요."),
    NO_MATCHING_ERROR_STATUS(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_002", "서버 에러. 일치하는 errorStatus를 찾을 수 없습니다."),
    REQUEST_BODY_INVALID(HttpStatus.BAD_REQUEST, "COMMON_003", "요청 본문을 읽을 수 없습니다. 빈 문자열 또는 null이 있는지 확인해주세요."),
    INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "COMMON_004", "요청한 날짜/시간 형식이 올바르지 않습니다. 형식을 확인해주세요."),
    DUPLICATE_UNIQUE_KEY(HttpStatus.CONFLICT, "COMMON_005", "이미 처리된 요청입니다."),

    // Auth 관련 에러
    AUTH_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_001", "토큰이 만료되었습니다."),
    AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_002", "토큰이 유효하지 않습니다."),
    INVALID_USERNAME_OR_PASSWORD(HttpStatus.UNAUTHORIZED, "AUTH_003", "이메일 또는 비밀번호가 유효하지 않습니다."),
    REQUEST_HEADER_INVALID(HttpStatus.BAD_REQUEST, "AUTH_004", "잘못된 요청 헤더입니다."),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "AUTH_005", "유저 인증에 실패했습니다."),
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, "AUTH_006", "해당 요청에 대한 권한이 없는 유저입니다."),
    LOGOUT_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_007", "로그아웃된 어세스 토큰 입니다"),
    TEMP_TOKEN_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "AUTH_014", "임시 토큰을 사용한 요청은 허용되지 않습니다."),
    INTERNAL_AUTH_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AUTH_009", "인증 처리 중 서버 에러가 발생했습니다."),

    // 카드 관련 응답
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "CARD_001", "해당 카드는 존재하지 않는 카드입니다."),
    // 같은 날짜에 같은 meal 중복 에러
    DUPLICATE_MEAL_CARD(HttpStatus.CONFLICT, "CARD_002", "이미 같은 날짜와 같은 식사 유형의 카드가 존재합니다."),
    CARD_UPDATE_FORBIDDEN(HttpStatus.FORBIDDEN, "CARD_003", "해당 카드를 수정할 수 있는 권한이 없습니다."),
    NO_RECENT_CARDS(HttpStatus.NOT_FOUND,"CARD_004","최근 7일간 작성된 피드가 없습니다,"),
    ALREADY_BOOKMARKED(HttpStatus.CONFLICT, "CARD_006", "이미 저장된 카드입니다."),
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "CARD_005", "카드를 저장한 기록이 없습니다"),
    IMAGE_REQUIRED(HttpStatus.BAD_REQUEST, "CARD_007", "카드에 이미지를 첨부해주세요."),
   

    // 댓글 관련 응답
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_001", "해당 댓글은 존재하지 않는 댓글입니다."),
    CURSOR_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMMENT_002", "유효하지 않은 커서입니다."),

    // s3 사진 첨부 에러
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_001", "파일 업로드에 실패했습니다."),

    // 유저 관련 응답
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER_001", "해당 유저는 존재하지 않는 유저입니다."),
    CARD_DELETE_FORBIDDEN(HttpStatus.FORBIDDEN, "USER_005", "해당 카드를 삭제할 권한이 없습니다.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .httpStatus(httpStatus)
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }
}
