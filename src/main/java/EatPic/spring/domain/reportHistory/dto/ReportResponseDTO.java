package EatPic.spring.domain.reportHistory.dto;

import EatPic.spring.domain.reportHistory.entity.ReportType;
import EatPic.spring.domain.reportHistory.entity.TargetType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
        @NotNull
        private Long reportHistoryId;
        @NotNull
        private Long userId;
        @NotNull
        private TargetType targetType;
        @NotNull
        private Long targetId;
        @NotNull
        private ReportType reportType;
    }
}
