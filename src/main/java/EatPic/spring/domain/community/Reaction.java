package EatPic.spring.domain.community;

import EatPic.spring.domain.community.enums.ReactionType;
import EatPic.spring.domain.newcard.Card;
import EatPic.spring.domain.user.User;
import EatPic.spring.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "reactions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"card_id", "user_id"}))
@IdClass(ReactionId.class)
public class Reaction extends BaseEntity {

    // 픽카드 FK
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    // 사용자 FK
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 반응 종류
    @Enumerated(EnumType.STRING)
    @Column(name = "reation_type", nullable = false)
    private ReactionType reactionType;
}
