package EatPic.spring.domain.notification.converter;

import EatPic.spring.domain.notification.dto.response.NotificationResponse.NotificationUserDTO;
import EatPic.spring.domain.notification.dto.response.NotificationResponse.RecentNotificationResponse;
import EatPic.spring.domain.notification.entity.Notification;
import EatPic.spring.domain.user.entity.User;

public class NotificationConverter {
  public static RecentNotificationResponse toRecentNotificationResponse(Notification notification, String cardImageUrl, Boolean isFollowing) {
    User sender = notification.getSender();

    return RecentNotificationResponse.builder()
        .notificationId(notification.getId())
        .type(notification.getType())
        .message(notification.getMessage())
        .createdAt(notification.getCreatedAt())
        .isRead(notification.isRead())
        .resourceId(notification.getResourceId())
        .cardImageUrl(cardImageUrl) // LIKE, COMMENT일 경우만
        .isFollowing(isFollowing)   // FOLLOW일 경우만
        .user(NotificationUserDTO.builder()
            .userId(sender.getId())
            .nickname(sender.getNickname())
            .profileImageUrl(sender.getProfileImageUrl())
            .build())
        .build();
  }

}
