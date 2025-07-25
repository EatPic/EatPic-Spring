package EatPic.spring.domain.user.mapping;

import EatPic.spring.domain.user.entity.User;
import EatPic.spring.global.common.code.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "user_follows",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "target_user_id"}))
@IdClass(UserFollowId.class)
public class UserFollow extends BaseEntity {

    // 사용자 FK
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 팔로우한 상대 사용자 FK
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id", nullable = false)
    private User targetUser;
}
