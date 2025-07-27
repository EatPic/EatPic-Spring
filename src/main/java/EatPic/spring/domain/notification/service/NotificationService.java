package EatPic.spring.domain.notification.service;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.notification.converter.NotificationConverter;
import EatPic.spring.domain.notification.dto.response.NotificationResponse.RecentNotificationResponse;
import EatPic.spring.domain.notification.entity.Notification;
import EatPic.spring.domain.notification.entity.NotificationType;
import EatPic.spring.domain.notification.repository.NotificationRepository;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.repository.UserFollowRepository;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.global.common.code.status.ErrorStatus;
import EatPic.spring.global.common.exception.handler.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
  private final CardRepository cardRepository;
  private final UserFollowRepository userFollowRepository;
  private final NotificationRepository notificationRepository;
  private final UserRepository userRepository;


  public List<RecentNotificationResponse> getRecentNotifications(Long userId) {
    User currentUser = userRepository.findById(userId).orElse(null);
    // 7일 전 기준 시각
    LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

    // 알림 목록 조회 (최근 7일 이내 + 최신순)
    List<Notification> notifications = notificationRepository
        .findByReceiverAndCreatedAtAfterOrderByCreatedAtDesc(currentUser, sevenDaysAgo);

    // 알림들을 DTO로 변환
    return notifications.stream()
        .map(notification -> convertToDto(notification, currentUser))
        .collect(Collectors.toList());
  }

  private RecentNotificationResponse convertToDto(Notification notification, User currentUser) {
    String cardImageUrl = null;
    Boolean isFollowing = null;

    // 카드 관련 알림일 경우: 카드 이미지 URL 조회
    if (notification.getType() == NotificationType.LIKE || notification.getType() == NotificationType.COMMENT) {
      cardImageUrl = cardRepository.findById(notification.getResourceId())
          .map(Card::getCardImageUrl)
          .orElse(null);
    }

    // FOLLOW 알림일 경우: 팔로우 상태 조회
    if (notification.getType() == NotificationType.FOLLOW) {
      isFollowing = userFollowRepository.existsByUserAndTargetUser(currentUser, notification.getSender());
    }

    // Converter 호출 (resourceId도 포함됨)
    return NotificationConverter.toRecentNotificationResponse(notification, cardImageUrl, isFollowing);
  }

  public void checkNotifications(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ExceptionHandler(ErrorStatus.USER_NOT_FOUND));

    user.updateLastNotificationCheckAt(LocalDateTime.now());
    userRepository.save(user);
  }

  public boolean isUnreadNotification(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ExceptionHandler(ErrorStatus.USER_NOT_FOUND));

    LocalDateTime lastChecked = user.getLastNotificationCheckAt();
    Notification latest = notificationRepository.findTopByReceiverOrderByCreatedAtDesc(user);

    return latest != null && (lastChecked == null || lastChecked.isBefore(latest.getCreatedAt()));
  }


}
