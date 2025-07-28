package EatPic.spring.domain.card.dto.response;

import EatPic.spring.domain.card.entity.Meal;
import EatPic.spring.domain.hashtag.entity.Hashtag;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
  @Schema(title = "CreateCardResponse: 카드 생성 시 응답 DTO")
  public static class CreateCardResponse {

    @Schema(description = "새로 생성한 카드 ID")
    private Long newcardId;

    @Schema(description = "카드 공개 여부")
    private Boolean isShared;

    @Schema(description = "해당 위치 위도")
    private BigDecimal latitude;

    @Schema(description = "해당 위치 경도")
    private BigDecimal longitude;

    @Schema(description = "카드 이미지 URL")
    private String cardImageUrl;

    @Schema(description = "레시피 URL")
    private String recipeUrl;

    @Schema(description = "메모")
    private String memo;

    @Schema(description = "레시피 내용")
    private String recipe;

    @Schema(description = "식사 종류 (breakfast, dinner, lunch, snack)")
    private Meal meal;
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

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(title = "CardFeedResponse: 카드 피드 상세 응답 DTO")
  public static class CardFeedResponse {

    @Schema(description = "카드 ID", example = "1001")
    private Long cardId;

    @Schema(description = "카드 이미지 URL", example = "https://cdn.eatpic.com/cards/123.jpg")
    private String imageUrl;

    @Schema(description = "식사 날짜", example = "2025-07-01")
    private LocalDate date;

    @Schema(description = "식사 시간", example = "13:10")
    private LocalTime time;

    @Schema(description = "식사 종류", example = "LUNCH")
    private Meal meal;

    @Schema(description = "기록 메모", example = "오늘은 샐러드를 먹었습니다~ 아보카도를 많이 넣어 먹었어요~~")
    private String memo;

    @Schema(description = "레시피 내용", example = "이 레시피는요 일단 야채들이 필요하구요...")
    private String recipe;

    @Schema(description = "레시피 링크", example = "https://recipe.example.com/salad-abc123")
    private String recipeUrl;

    @Schema(description = "카드 작성 위치 위도", example = "37.12345678")
    private BigDecimal latitude;

    @Schema(description = "카드 작성 위치 경도", example = "127.98765432")
    private BigDecimal longitude;

    @Schema(description = "위치 텍스트", example = "캐나다라마바사아자차카파타하가나다라")
    private String locationText;

    @Schema(description = "해시태그 목록", example = "[\"#아침\", \"#다아섯글자\"]")
    private List<String> hashtags;

    @Schema(description = "카드 작성자 정보")
    private CardFeedUserDTO user;

    @Schema(description = "전체 반응 수", example = "123")
    private int reactionCount;

    @Schema(description = "현재 사용자가 선택한 반응", example = "YUMMY")
    private String userReaction;

    @Schema(description = "댓글 수", example = "3")
    private int commentCount;

    @Schema(description = "북마크 여부", example = "true")
    private boolean isBookmarked;
  }

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(title = "CardFeedUserDTO: 카드 작성자 정보")
  public static class CardFeedUserDTO {

    @Schema(description = "작성자 유저 ID", example = "5")
    private Long userId;

    @Schema(description = "닉네임", example = "잇콩")
    private String nickname;

    @Schema(description = "프로필 이미지 URL", example = "https://cdn.eatpic.com/profile/user5.jpg")
    private String profileImageUrl;
  }

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class profileCardListDTO{
    private Long userId;
    private boolean hasNext;
    private Long nextCursor;
    private List<ProfileCardDTO> cardsList;
  }

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ProfileCardDTO {
    private Long cardId;
    private String cardImageUrl;
  }





}
