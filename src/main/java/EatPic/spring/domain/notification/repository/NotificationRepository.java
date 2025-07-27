package EatPic.spring.domain.notification.repository;

import EatPic.spring.domain.notification.entity.Notification;
import EatPic.spring.domain.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

  List<Notification> findByReceiverAndCreatedAtAfterOrderByCreatedAtDesc(User receiver, LocalDateTime dateTime);

  Notification findTopByReceiverOrderByCreatedAtDesc(User receiver);
}
