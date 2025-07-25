package EatPic.spring.domain.reaction.entity;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.global.common.code.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
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
