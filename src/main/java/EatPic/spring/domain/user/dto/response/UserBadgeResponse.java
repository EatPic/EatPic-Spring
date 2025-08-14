package EatPic.spring.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Schema(name = "UserBadgeResponse", description = "사용자 뱃지 관련 DTO")
public class UserBadgeResponse {

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(title = "UserBadgeResponse: 최근 알림 응답 DTO")
  public static class HomeBadgeResponse {

    @Schema(description = "뱃지 ID", example = "1")
    @NotNull
    private Long userBadgeId;

    @Schema(description = "뱃지 이름", example = "공유잼")
    @NotNull
    private String badgeName;

    @Schema(description = "뱃지 이미지 URL", example = "https://cdn.eatpic.com/badges/badge_1.jpg")
    @NotNull
    private String badgeImageUrl;

    @Schema(description = "진행률 (0~100)", example = "70")
    @NotNull
    private int progressRate;

    @Schema(description = "뱃지 획득 여부", example = "true")
    @NotNull
    private boolean isAchieved;



  }

  @Schema(title = "BadgeDetailResponseDTO")
  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class BadgeDetailResponseDTO {

    @Schema(description = "유저 뱃지 ID", example = "10")
    private Long userBadgeId;

    @Schema(description = "뱃지 이름", example = "삼시세끼")
    private String badgeName;

    @Schema(description = "뱃지 설명", example = "하루 세끼를 모두 기록하면 받을 수 있어요!")
    private String badgeDescription;

    @Schema(description = "뱃지 이미지 URL", example = "https://cdn.eatpic.com/badges/badge10.png")
    private String badgeImageUrl;

    @Schema(description = "진행률", example = "70")
    private Integer progressRate;

    @Schema(description = "완료 여부", example = "true")
    private Boolean isAchieved;

    @Schema(description = "현재 달성 수치", example = "7")
    private Integer currentValue;

    @Schema(description = "필요 수치", example = "10")
    private Integer conditionValue;
  }

}
