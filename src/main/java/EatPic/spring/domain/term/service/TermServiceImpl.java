package EatPic.spring.domain.term.service;

import EatPic.spring.domain.term.Repository.TermRepository;
import EatPic.spring.domain.term.converter.TermConverter;
import EatPic.spring.domain.term.dto.TermDTO;
import EatPic.spring.domain.term.entity.Term;
import EatPic.spring.global.common.code.status.ErrorStatus;
import EatPic.spring.global.common.exception.handler.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static EatPic.spring.global.common.code.status.ErrorStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermServiceImpl implements TermService {

    private final TermRepository termRepository;

    @Override
    public List<TermDTO> getTerms() {
        return termRepository.findLastByTermType()
                .stream()
                .map(TermConverter::toTermsDTO)
                .toList();
    }

    @Override
    public TermDTO.TermsDetailDTO getTermsDetail(Long id) {
        Term term = termRepository.findById(id)
                .orElseThrow(() -> new ExceptionHandler(TERM_NOT_FOUND));
        return TermConverter.toTermsDetailDTO(term);
    }
}
