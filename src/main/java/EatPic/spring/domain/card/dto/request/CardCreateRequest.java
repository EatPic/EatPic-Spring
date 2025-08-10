package EatPic.spring.domain.card.dto.request;

import EatPic.spring.domain.card.entity.Meal;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;

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

    @JsonProperty("isShared")
    private Boolean isShared;

    @JsonProperty("latitude")
    private BigDecimal latitude;

    @JsonProperty("longitude")
    private BigDecimal longitude;

    // 파일(cardImageFile)을 별도로
//    @JsonProperty("cardImageUrl")
//    private String cardImageUrl;

    @JsonProperty("recipeUrl")
    private String recipeUrl;

    @JsonProperty("memo")
    private String memo;

    @JsonProperty("recipe")
    private String recipe;

    @JsonProperty("meal")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Meal meal; // Enum 매핑 시 대소문자 주의

    private List<String> hashtags; // 사용자가 선택/생성한 해시태그 목록 (이름 기준)
  }


  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(description = "픽카드 수정 요청 DTO")
  public static class CardUpdateRequest {

    @Schema(description = "나의 메모", example = "오늘은 샐러드를 먹었습니다~ 아보카도를 많이 넣었어요")
    private String memo;

    @Schema(description = "레시피 내용", example = "야채, 아보카도, 소스 조합으로 구성된 샐러드입니다.")
    private String recipe;

    @Schema(description = "레시피 링크 URL", example = "https://example.com/recipe/123")
    private String recipeUrl;

    @Schema(description = "위도", example = "37.5665")
    private BigDecimal latitude;

    @Schema(description = "경도", example = "126.9780")
    private BigDecimal longitude;

    @Schema(description = "장소 이름 텍스트", example = "서울특별시 성북구 정릉동")
    private String locationText;

    @Schema(description = "피드 공개 여부 (true = 공개, false = 비공개)", example = "true")
    private Boolean isShared;

  }

}
