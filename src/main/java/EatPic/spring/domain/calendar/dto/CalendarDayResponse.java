package EatPic.spring.domain.calendar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "CalendarDayResponse: 날짜별 대표 이미지 응답 DTO")
public class CalendarDayResponse {
  @Schema(description = "해당 날짜", example = "2025-07-02")
  private LocalDate date;

  @Schema(description = "대표 이미지 URL", example = "https://example.com/image.jpg")
  private String imageUrl;

}
