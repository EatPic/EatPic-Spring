package EatPic.spring.domain.card.dto.response;

import EatPic.spring.domain.card.entity.Meal;
import EatPic.spring.domain.hashtag.entity.Hashtag;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @NotNull
    private Long newcardId;

    @Schema(description = "카드 공개 여부")
    @NotNull
    private Boolean isShared;

    @Schema(description = "해당 위치 위도")
    @NotNull
    private BigDecimal latitude;

    @Schema(description = "해당 위치 경도")
    @NotNull
    private BigDecimal longitude;

    @Schema(description = "카드 이미지 URL")
    @NotNull
    private String cardImageUrl;

    @Schema(description = "레시피 URL")
    @NotNull
    private String recipeUrl;

    @Schema(description = "메모")
    @NotNull
    private String memo;

    @Schema(description = "레시피 내용")
    @NotNull
    private String recipe;

    @Schema(description = "식사 종류 (breakfast, dinner, lunch, snack)")
    @NotNull
    private Meal meal;
  }

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(title = "CardDetailResponse: 카드 상세 응답 DTO")
  @NotNull
  public static class CardDetailResponse {

    @Schema(description = "카드 ID", example = "1")
    @NotNull
    private Long cardId;

    @Schema(description = "카드 이미지 URL", example = "https://cdn.eatpic.com/cards/123.jpg")
    @NotNull
    private String imageUrl;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "날짜/시간", example = "2025-08-09 08:17:29")
    private LocalDateTime datetime;

    @Schema(description = "식사 종류", example = "BREAKFAST")
    @NotNull
    private Meal mealType;

    @Schema(description = "레시피 링크", example = "https://recipe.example.com/salad-abc123")
    @NotNull
    private String recipeUrl;

    @Schema(description = "위도", example = "37.4979")
    @NotNull
    private BigDecimal latitude;

    @Schema(description = "경도", example = "127.0276")
    @NotNull
    private BigDecimal longitude;

    @Schema(description = "장소 이름", example = "장소 이름")
    @NotNull
    private String locationText;

    @Schema(description = "기록 메모", example = "오늘은 샐러드를 먹었습니다~ 아보카도를 많이 넣어 먹었어요~~")
    @NotNull
    private String memo;

    @Schema(description = "레시피 내용", example = "이 레시피는요 일단 야채들이 필요하고요...")
    @NotNull
    private String recipe;

    @Schema(description = "다음 식사 카드 정보")
    @NotNull
    private NextMealCard nextMeal;
  }

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(title = "NextMealCard: 다음 식사 카드 요약")
  public static class NextMealCard {

    @Schema(description = "다음 식사 카드 ID", example = "2")
    @NotNull
    private Long cardId;
  }

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(title = "CardFeedResponse: 카드 피드 상세 응답 DTO")
  public static class CardFeedResponse {

    @Schema(description = "카드 ID", example = "1001")
    @NotNull
    private Long cardId;

    @Schema(description = "카드 이미지 URL", example = "https://cdn.eatpic.com/cards/123.jpg")
    @NotNull
    private String imageUrl;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // JSON 변환 시 포맷 지정
    @Schema(description = "날짜/시간", example = "2025-08-09T08:17:29")
    private LocalDateTime datetime;

    @Schema(description = "식사 종류", example = "LUNCH")
    @NotNull
    private Meal meal;

    @Schema(description = "기록 메모", example = "오늘은 샐러드를 먹었습니다~ 아보카도를 많이 넣어 먹었어요~~")
    @NotNull
    private String memo;

    @Schema(description = "레시피 내용", example = "이 레시피는요 일단 야채들이 필요하구요...")
    @NotNull
    private String recipe;

    @Schema(description = "레시피 링크", example = "https://recipe.example.com/salad-abc123")
    @NotNull
    private String recipeUrl;

    @Schema(description = "카드 작성 위치 위도", example = "37.12345678")
    @NotNull
    private BigDecimal latitude;

    @Schema(description = "카드 작성 위치 경도", example = "127.98765432")
    @NotNull
    private BigDecimal longitude;

    @Schema(description = "위치 텍스트", example = "캐나다라마바사아자차카파타하가나다라")
    @NotNull
    private String locationText;

    @Schema(description = "해시태그 목록", example = "[\"#아침\", \"#다아섯글자\"]")
    @NotNull
    private List<String> hashtags;

    @Schema(description = "카드 작성자 정보")
    @NotNull
    private CardFeedUserDTO user;

    @Schema(description = "전체 반응 수", example = "123")
    @NotNull
    private int reactionCount;

    @Schema(description = "현재 사용자가 선택한 반응", example = "YUMMY")
    private String userReaction;

    @Schema(description = "댓글 수", example = "3")
    @NotNull
    private int commentCount;

    @Schema(description = "북마크 여부", example = "true")
    @NotNull
    private boolean isBookmarked;
  }

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(title = "CardFeedUserDTO: 카드 작성자 정보")
  public static class CardFeedUserDTO {

    @Schema(description = "작성자 유저 ID", example = "5")
    @NotNull
    private Long userId;

    @Schema(description = "아이디", example= "naniianiida")
    private String nameId;

    @Schema(description = "닉네임", example = "잇콩")
    @NotNull
    private String nickname;

    @Schema(description = "프로필 이미지 URL", example = "https://cdn.eatpic.com/profile/user5.jpg")
    @NotNull
    private String profileImageUrl;
  }

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(title = "TodayCardResponse: 오늘의 식사(카드) 현황 응답 dto")
  public static class TodayCardResponse {
    @Schema(description = "카드 ID", example = "12")
    @NotNull
    private Long cardId;

    @Schema(description = "카드 이미지 URL", example = "https://cdn.eatpic.com/cards/12.jpg")
    @NotNull
    private String cardImageUrl;

    @Schema(description = "식사 종류", example = "BREAKFAST")
    @NotNull
    private Meal meal;
  }

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PagedCardFeedResponseDto{
    private Long selectedId;
    @NotNull
    private boolean hasNext;
    private Long nextCursor;
    @NotNull
    private List<CardFeedResponse> cardFeedList;
  }

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(title = "RecommendCardResponse: 추천 카드 응답 dto")
  public static class RecommendCardResponse {
    @Schema(description = "카드 ID", example = "12")
    @NotNull
    private Long cardId;

    @Schema(description = "카드 이미지 URL", example = "https://cdn.eatpic.com/cards/12.jpg")
    @NotNull
    private String cardImageUrl;

  }

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(title = "CardDeleteResponse: 카드 삭제 응답 dto")
  public static class CardDeleteResponse {
    @Schema(description = "카드 ID", example = "12")
    @NotNull
    private Long cardId;

    @Schema(description = "카드 삭제 성공 메세지", example = "카드 삭제 성공")
    @NotNull
    private String successMessage;

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
