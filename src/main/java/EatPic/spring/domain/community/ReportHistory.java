package EatPic.spring.domain.community;

import EatPic.spring.domain.community.enums.ReportType;
import EatPic.spring.domain.community.enums.TargetType;
import EatPic.spring.domain.user.User;
import EatPic.spring.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "report_histories")
public class ReportHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_history_id", nullable = false)
    private long id;

    // 신고 대상 종류
    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private TargetType targetType;

    // 신고 내용 종류
    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false)
    private ReportType reportType;

    // 신고당한 픽카드/사용자 FK
    @Column(name = "target_id", nullable = false)
    private Long targetId;

    // 신고한 사용자 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_user_id", nullable = false)
    private User reporter;

}
