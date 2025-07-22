package EatPic.spring.domain.user.mapping;

import EatPic.spring.domain.badge.entity.Badge;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.global.common.code.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "user_badges")
public class UserBadge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_badge_id", nullable = false)
    private Long userBadgeId;

    // 사용자 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 뱃지 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id", nullable = false)
    private Badge badge;

    // 완료한 항목 수(수치)
    @Column(name = "current_value", nullable = false)
    private Integer currentValue = 0;       // default: 0

    // 퍼센트 진도율
    @Column(name = "progresss_rate", nullable = false)
    private Integer progressRate = 0;       // default: 0

    // 뱃지 획득 여부
    @Column(name = "is_achieved", nullable = false)
    private boolean isAchieved = false;     // default: false
}
