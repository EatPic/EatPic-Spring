package EatPic.spring.domain.term.entity;

import EatPic.spring.domain.user.mapping.UserTerm;
import EatPic.spring.global.common.code.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "terms")
public class Term extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_id", nullable = false)
    private Long id;

    // 약관 종류
    @Enumerated(EnumType.STRING)
    @Column(name = "term_type", nullable = false)
    private TermType termType;

    // 약관 제목
    @Column(name = "title", length = 255, nullable = true)
    private String title;

    // 약관 내용
    @Column(name = "content", nullable = true, columnDefinition = "TEXT")
    private String content;

    // 필수 여부
    @Column(name = "is_required", nullable = false)
    private boolean isRequired;

    // 약관 버전 (1.0, 2.1)
    @Column(name = "version", length = 20, nullable = false)
    private String version;

    @OneToMany(mappedBy = "term")
    private List<UserTerm> userTerms = new ArrayList<>();

}
