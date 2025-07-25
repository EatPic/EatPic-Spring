package EatPic.spring.domain.card.dto.request;

import EatPic.spring.domain.card.entity.Meal;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(title = "CardCreateRequest: 카드 생성 요청 DTO") //스웨서 문서화를 위한 어노테이션!
public class CardCreateRequest {

  // 픽카드 기록 작성하기
  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CreateCardRequest {
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
