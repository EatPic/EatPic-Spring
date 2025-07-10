package EatPic.spring.domain.card.mapping;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.hashtag.entity.Hashtag;
import EatPic.spring.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "card_hashtags")
public class CardHashtag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_hashtag_id", nullable = false)
    private Long id;

    // 픽카드 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    // 해시태그 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id", nullable = false)
    private Hashtag hashtag;
}
