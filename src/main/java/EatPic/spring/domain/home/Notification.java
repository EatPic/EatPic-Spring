package EatPic.spring.domain.home;

import EatPic.spring.domain.home.enums.NotificationType;
import EatPic.spring.domain.user.User;
import EatPic.spring.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "notifications")
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false)
    private Long id;

    // 알림 받은 사용자 FK
    @ManyToOne
    @JoinColumn(name = "received_user_id", nullable = false)
    private User receiver;

    // 알림 보낸 사용자 FK
    @ManyToOne
    @JoinColumn(name = "sent_user_id", nullable = false)
    private User sender;

    // 알림 타입
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    private NotificationType type;

    // 알림 메세지
    @Column(name = "message",length = 255, nullable = false)
    private String message;

    // 관련 리소스 아이디
    @Column(name = "resource_id", nullable = true)
    private Integer resourceId;

    // 읽음 여부
    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;     // default: false
}
