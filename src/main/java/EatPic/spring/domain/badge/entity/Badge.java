package EatPic.spring.domain.badge.entity;

import EatPic.spring.domain.user.mapping.UserBadge;
import EatPic.spring.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "badges")
public class Badge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id", nullable = false)
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "description", length = 100, nullable = false)
    private String description;

    // 뱃지 확득 조건 타입
    @Enumerated(EnumType.STRING)
    @Column(name = "condition_type", nullable = false)
    private ConditionType conditionType;

    // 뱃지 확득 조건 값(수치)
    @Enumerated(EnumType.STRING)
    @Column(name = "condition_value", nullable = false)
    private ConditionValue conditionValue;

    @OneToMany(mappedBy = "badge")
    private List<UserBadge> userBadges = new ArrayList<>();

}
