package EatPic.spring.domain.calendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "CalendarDayResponse: 날짜별 대표 이미지 응답 DTO")
public class CalendarDayResponse {
  @Schema(description = "해당 날짜", example = "2025-07-02")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @NotNull
  private LocalDate date;

  @Schema(description = "대표 이미지 URL", example = "https://example.com/image.jpg")
  @NotNull
  private String imageUrl;

  @Schema(description = "카드 ID", example = "123")
  @NotNull
  private Long cardId;

}
