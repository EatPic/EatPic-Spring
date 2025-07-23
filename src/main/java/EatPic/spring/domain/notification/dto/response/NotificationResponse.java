package EatPic.spring.domain.notification.dto.response;

import EatPic.spring.domain.notification.entity.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Schema(title = "NotificationResponse: 알림 응답 DTO")
public class NotificationResponse {

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(title = "NotificationResponse: 최근 알림 응답 DTO")
  public static class RecentNotificationResponse {

    @Schema(description = "알림 ID", example = "1")
    private Long notificationId;

    @Schema(description = "알림 타입", example = "LIKE")
    private NotificationType type;

    @Schema(description = "알림 메시지", example = "sososo 님이 회원님의 식사 기록을 좋아합니다.")
    private String message;

    @Schema(description = "알림 생성 시각", example = "2025-07-09T01:05:00")
    private LocalDateTime createdAt;

    @Schema(description = "알림 읽음 여부", example = "false")
    private boolean isRead;

    @Schema(description = "리소스 ID (카드 ID)", example = "6")
    private Long resourceId;

    @Schema(description = "알림 보낸 사용자 정보")
    private NotificationUserDTO user;

    @Schema(description = "해당 카드 이미지 URL (LIKE, COMMENT 타입인 경우에만 사용)", example = "https://cdn.eatpic.com/cards/123.jpg")
    private String cardImageUrl; // FOLLOW일 경우 null

    @Schema(description = "팔로우 상태 여부 (FOLLOW 타입일 경우)", example = "true")
    private Boolean isFollowing; // FOLLOW일 경우 true or false, 다른 타입은 null
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(title = "NotificationUserDTO: 알림 보낸 사용자 정보")
  public static class NotificationUserDTO {

    @Schema(description = "보낸 유저 ID", example = "123")
    private Long userId;

    @Schema(description = "닉네임", example = "잇콩")
    private String nickname;

    @Schema(description = "프로필 이미지 URL", example = "https://cdn.eatpic.com/profile/123.jpg")
    private String profileImageUrl;
  }

}
