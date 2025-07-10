package EatPic.spring.domain.card.entity;

import EatPic.spring.domain.card.mapping.CardHashtag;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "cards")
public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", nullable = false)
    private Long id;

    // 사용자 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 픽카드 이미지 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_image_id", nullable = false)
    private CardImage cardImage;

    // 식사 종류
    @Enumerated(EnumType.STRING)
    @Column(name = "meal", nullable = false)
    private Meal meal;

    @Column(name = "memo", nullable = true, columnDefinition = "TEXT")
    private String memo;

    @Column(name = "recipe", nullable = true, columnDefinition = "TEXT")
    private String recipe;

    @Column(name = "recipe_url", length = 500, nullable = true)
    private String recipeUrl;

    // 위치 위경도로 제공
    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    // 피드 공유 여부
    @Column(name = "isShared", nullable = false)
    private Boolean isShared = true;        // default: true

    @OneToMany(mappedBy = "card")
    private List<CardHashtag> cardHashtags = new ArrayList<>();

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CardImage> cardImages = new ArrayList<>();

}
