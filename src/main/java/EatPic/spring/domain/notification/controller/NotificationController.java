package EatPic.spring.domain.notification.controller;

import EatPic.spring.domain.notification.dto.response.NotificationResponse.RecentNotificationResponse;
import EatPic.spring.domain.notification.service.NotificationService;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.service.UserService;
import EatPic.spring.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
@Tag(name = "Notification", description = "알림 관련 API")
public class NotificationController {
  private final NotificationService notificationService;
  private final UserService userService;

  @GetMapping("/recent")
  @Operation(summary = "최근 알림 목록 조회 (7일 이내)", description = "현재 로그인한 사용자의 최근 알림을 조회합니다.")
  public List<RecentNotificationResponse> getRecentNotifications() {
    Long userId = 1L; //Long userId = userDetails.getUser().getId(); //로그인 구현 시 이렇게 바꾸기
    //User user = userDetails.getUser(); //아니면 이렇게 해서 객체 자체를 넘기기 (이게 일반적인 방법)
    return notificationService.getRecentNotifications(userId);
  }

  @PostMapping("/check")
  @Operation(summary = "알림 확인 시간 업데이트", description = "사용자가 알림 화면에 들어가면 마지막 확인 시간을 업데이트합니다.")
  public ApiResponse<Void> checkNotifications() {
    Long userId = 1L; // 로그인 기능 구현 전 임시 사용자
    notificationService.checkNotifications(userId);
    return ApiResponse.onSuccess(null);
  }

  @GetMapping("/unread-status")
  @Operation(summary = "새 알림 여부 확인", description = "최근 알림 생성 시각과 마지막 확인 시각을 비교하여 새 알림 여부를 반환합니다.")
  public ApiResponse<Map<String, Boolean>> getUnreadStatus() {
    Long userId = 1L; // 로그인 기능 구현 전 임시 사용자
    boolean isUnread = notificationService.isUnreadNotification(userId);
    return ApiResponse.onSuccess(Map.of("isUnread", isUnread));
  }


}
