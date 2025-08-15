package EatPic.spring.domain.term.controller;

import EatPic.spring.domain.term.dto.TermDTO;
import EatPic.spring.domain.term.service.TermService;
import EatPic.spring.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/terms")
@Tag(name = "Terms", description = "약관 조회 관련 API")
public class TermController {

    private final TermService termService;

    // 회원 가입 약관 동의 화면
    @GetMapping("")
    @Operation(summary = "약관 목록 조회")
    public ApiResponse<List<TermDTO>> getTerms() {
        List<TermDTO> terms = termService.getTerms();
        return ApiResponse.onSuccess(terms);
    }

    // 약관 상세 보기
    @GetMapping("/{termId}")
    @Operation(summary = "약관 상세")
    public ApiResponse<TermDTO.TermsDetailDTO> getTermDetail(@PathVariable Long termId) {
        return ApiResponse.onSuccess(termService.getTermsDetail(termId));
    }
}
