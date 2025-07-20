package EatPic.spring.domain.reportHistory.dto;

import EatPic.spring.domain.reportHistory.entity.ReportType;
import EatPic.spring.domain.reportHistory.entity.TargetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReportResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReportResultResponseDTO{
        private Long reportHistoryId;
        private Long userId;
        private TargetType targetType;
        private Long targetId;
        private ReportType reportType;
    }
}
