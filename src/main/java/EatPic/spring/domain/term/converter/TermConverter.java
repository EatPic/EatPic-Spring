package EatPic.spring.domain.term.converter;

import EatPic.spring.domain.term.dto.TermDTO;
import EatPic.spring.domain.term.entity.Term;

public class TermConverter {
    public static TermDTO toTermsDTO(Term term){
        return TermDTO.builder()
                .id(term.getId())
                .type(term.getTermType())
                .title(term.getTitle())
                .isRequired(term.isRequired())
                .version(term.getVersion())
                .build();
    }

    public static TermDTO.TermsDetailDTO toTermsDetailDTO(Term term){
        return TermDTO.TermsDetailDTO.builder()
                .id(term.getId())
                .type(term.getTermType())
                .title(term.getTitle())
                .content(term.getContent())
                .version(term.getVersion())
                .isRequired(term.isRequired())
                .build();
    }
}
