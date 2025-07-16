package EatPic.spring.domain.user.mapping;

import EatPic.spring.domain.term.entity.Term;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "user_terms")
public class UserTerm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_term_id", nullable = false)
    private long id;

    // 사용자 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 약관 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id", nullable = false)
    private Term term;

    // 약관 동의 여부
    @Column(name = "is_agreed", nullable = false)
    private boolean isAgreed;
}
