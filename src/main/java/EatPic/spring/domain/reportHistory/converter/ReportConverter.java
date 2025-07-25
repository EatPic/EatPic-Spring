package EatPic.spring.domain.reportHistory.converter;

import EatPic.spring.domain.reportHistory.dto.ReportResponseDTO;
import EatPic.spring.domain.reportHistory.entity.ReportHistory;

public class ReportConverter {
    public static ReportResponseDTO.ReportResultResponseDTO toReportResultResponseDTO(ReportHistory reportHistory){
        return ReportResponseDTO.ReportResultResponseDTO.builder()
                .reportHistoryId(reportHistory.getId())
                .userId(reportHistory.getReporter().getId())
                .targetType(reportHistory.getTargetType())
                .targetId(reportHistory.getTargetId())
                .reportType(reportHistory.getReportType())
                .build();
    }
}
