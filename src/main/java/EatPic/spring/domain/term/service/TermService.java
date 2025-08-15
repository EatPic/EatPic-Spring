package EatPic.spring.domain.term.service;

import EatPic.spring.domain.term.dto.TermDTO;

import java.util.List;

public interface TermService {
    List<TermDTO> getTerms();
    TermDTO.TermsDetailDTO getTermsDetail(Long termId);
}
