package EatPic.spring.domain.card.dto.response;

import EatPic.spring.domain.card.entity.Meal;
import io.swagger.v3.oas.annotations.media.Schema;
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
}
