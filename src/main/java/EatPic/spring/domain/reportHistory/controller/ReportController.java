package EatPic.spring.domain.reportHistory.controller;

import EatPic.spring.domain.reaction.converter.ReactionConverter;
import EatPic.spring.domain.reaction.dto.ReactionResponseDTO;
import EatPic.spring.domain.reaction.entity.Reaction;
import EatPic.spring.domain.reportHistory.dto.ReportResponseDTO;
import EatPic.spring.domain.reportHistory.entity.ReportType;
import EatPic.spring.domain.reportHistory.entity.TargetType;
import EatPic.spring.domain.reportHistory.service.ReportService;
import EatPic.spring.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
@Tag(name = "ReportHistory", description = "신고 관련 API")
public class ReportController {

    private final ReportService reportService;

    @Operation(
            summary = "신고하기",
            description = ReportType.SWAGGER_DESCRIPTION)
    @PostMapping("")
    public BaseResponse<ReportResponseDTO.ReportResultResponseDTO> createReport(@RequestParam("id")Long id,
                                                                                  @RequestParam("신고 대상")TargetType targetType,
                                                                                  @RequestParam("신고 타입")ReportType reportType){

        return BaseResponse.onSuccess(reportService.createReport(targetType,id,reportType));
    }
}
