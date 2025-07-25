package EatPic.spring.domain.reportHistory.entity;

import lombok.Getter;

@Getter
public enum ReportType {
    PROFANITY("욕설 또는 비방"),
    OBSCENITY("음란성/선정적 내용"),
    SPAM("도배 또는 광고성 게시물"),
    MISINFORMATION("거짓 정보 또는 허위 사실"),
    OFFENSIVE("불쾌감을 주는 이미지 또는 언행"),
    COPYRIGHT("저작권 침해");
    private final String description;
    ReportType(String description) {
        this.description = description;
    }

    public static final String SWAGGER_DESCRIPTION =
            "신고타입<br>" +
                    "<ul>" +
                    "<li>`PROFANITY`: 욕설 또는 비방</li>" +
                    "<li>`OBSCENITY`: 음란성/선정적 내용</li>" +
                    "<li>`SPAM`: 도배 또는 광고성 게시물</li>" +
                    "<li>`MISINFORMATION`: 거짓 정보 또는 허위 사실</li>" +
                    "<li>`OFFENSIVE`: 불쾌감을 주는 이미지 또는 언행</li>" +
                    "<li>`COPYRIGHT`: 저작권 침해</li>" +
                    "</ul>";

}
