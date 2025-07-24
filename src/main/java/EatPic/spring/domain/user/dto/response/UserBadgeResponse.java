package EatPic.spring.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private Long userBadgeId;

    @Schema(description = "뱃지 이름", example = "공유잼")
    private String badgeName;

    @Schema(description = "뱃지 이미지 URL", example = "https://cdn.eatpic.com/badges/badge_1.jpg")
    private String badgeImageUrl;

    @Schema(description = "진행률 (0~100)", example = "70")
    private int progressRate;

    @Schema(description = "뱃지 획득 여부", example = "true")
    private boolean isAchieved;



  }

}
