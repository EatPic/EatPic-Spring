package EatPic.spring.domain.card.dto.response;

import EatPic.spring.domain.card.entity.Meal;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@Schema(title = "CardResponse: 카드 응답 DTO")
public class CardResponse {

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateCardResponse {
    private Long newcardId;
    private Boolean isShared;           // 공개 여부
    private BigDecimal latitude;            // 위도
    private BigDecimal longitude;           // 경도
    private String cardImageUrl;           // 카드 이미지 URL
    private String recipeUrl;           // 레시피 URL
    private String memo;                // 메모
    private String recipe;              // 레시피 내용
    private Meal meal;                // 식사 종류 (breakfast, dinner, lunch, snack)
  }

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(title = "CardDetailResponse: 카드 상세 응답 DTO")
  public static class CardDetailResponse {

    @Schema(description = "카드 ID", example = "1")
    private Long cardId;

    @Schema(description = "카드 이미지 URL", example = "https://cdn.eatpic.com/cards/123.jpg")
    private String imageUrl;

    @Schema(description = "식사 날짜", example = "2025-07-01")
    private LocalDate date;

    @Schema(description = "식사 시간", example = "10:10")
    private LocalTime time;

    @Schema(description = "식사 종류", example = "BREAKFAST")
    private Meal mealType;

    @Schema(description = "레시피 링크", example = "https://recipe.example.com/salad-abc123")
    private String recipeUrl;

    @Schema(description = "위도", example = "37.4979")
    private BigDecimal latitude;

    @Schema(description = "경도", example = "127.0276")
    private BigDecimal longitude;

    @Schema(description = "기록 메모", example = "오늘은 샐러드를 먹었습니다~ 아보카도를 많이 넣어 먹었어요~~")
    private String memo;

    @Schema(description = "레시피 내용", example = "이 레시피는요 일단 야채들이 필요하고요...")
    private String recipe;

    @Schema(description = "다음 식사 카드 정보")
    private NextMealCard nextMeal;
  }

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(title = "NextMealCard: 다음 식사 카드 요약")
  public static class NextMealCard {

    @Schema(description = "다음 식사 카드 ID", example = "2")
    private Long cardId;
  }



}
