package EatPic.spring.domain.reportHistory.service;

import EatPic.spring.domain.reportHistory.dto.ReportResponseDTO;
import EatPic.spring.domain.reportHistory.entity.ReportType;
import EatPic.spring.domain.reportHistory.entity.TargetType;
import jakarta.servlet.http.HttpServletRequest;

public interface ReportService {
    ReportResponseDTO.ReportResultResponseDTO createReport(HttpServletRequest request, TargetType targetType, Long targetId, ReportType reportType);
}
