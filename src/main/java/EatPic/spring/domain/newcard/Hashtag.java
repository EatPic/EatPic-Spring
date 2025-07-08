package EatPic.spring.domain.newcard;

import EatPic.spring.domain.newcard.mapping.CardHashtag;
import EatPic.spring.domain.user.User;
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
@Table(name = "hashtags")
public class Hashtag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id", nullable = false)
    private Long id;

    // 사용자 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "hashtag_name", length = 5, nullable = false)
    private String hashtagName;

    @OneToMany(mappedBy = "hashtag")
    private List<CardHashtag> cardHashtags = new ArrayList<>();

}
