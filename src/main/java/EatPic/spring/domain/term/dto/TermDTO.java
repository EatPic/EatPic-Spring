package EatPic.spring.domain.term.dto;

import EatPic.spring.domain.term.entity.TermType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TermDTO {
    // 약관 동의 리스트 화면
    private Long id;
    private TermType type;          // terms_agreed 등 약관 종류
    private String title;
    private boolean isRequired;
    private String version;

    @Getter
    @Builder
    public static class TermsDetailDTO{
        // 약관 상세 보기
        private Long id;
        private TermType type;
        private String title;
        private String content;     // 약관 전문
        private boolean isRequired;
        private String version;
    }
}


