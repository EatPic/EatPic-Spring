package EatPic.spring.domain.card.entity;

import EatPic.spring.domain.card.mapping.CardHashtag;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.global.common.code.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
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

    // 픽카드 이미지
    @Column(name = "card_image_url", length = 500, nullable = false)
    private String cardImageUrl;

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

    @Column(name = "location_text", length = 500, nullable = true)
    private String locationText; // 위치에 따른 장소 이름 추가

    // 피드 공유 여부
    @Column(name = "isShared", nullable = false)
    private Boolean isShared = true;        // default: true

    // 해시태그 관련 리스트
    @OneToMany(mappedBy = "card")
    private List<CardHashtag> cardHashtags = new ArrayList<>();

    // 삭제 여부 (Soft Delete 용도)
    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    // 삭제 시간 (선택, 감사용)
    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void update(
        String memo,
        String recipe,
        String recipeUrl,
        BigDecimal latitude,
        BigDecimal longitude,
        String locationText,
        Boolean isShared
    ) {
        if (memo != null) this.memo = memo;
        if (recipe != null) this.recipe = recipe;
        if (recipeUrl != null) this.recipeUrl = recipeUrl;
        if (latitude != null) this.latitude = latitude;
        if (longitude != null) this.longitude = longitude;
        if (locationText != null) this.locationText = locationText;
        if (isShared != null) this.isShared = isShared;
    }

    // 뱃지 조건 확인용 메서드들
    public boolean hasLocation() {
        return this.latitude != null && this.longitude != null;
    }

    public boolean hasRecipeUrl() {
        return this.recipeUrl != null;
    }

    public boolean containsHashtag(String targetTag) {
        if (this.cardHashtags == null || this.cardHashtags.isEmpty()) return false;

        return cardHashtags.stream()
            .anyMatch(cardHashtag -> cardHashtag.getHashtag().getHashtagName().equalsIgnoreCase(targetTag));
    }

}
