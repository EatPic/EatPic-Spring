package EatPic.spring.domain.user;

import EatPic.spring.domain.home.mapping.UserBadge;
import EatPic.spring.domain.user.enums.SocialType;
import EatPic.spring.domain.user.enums.UserStatus;
import EatPic.spring.domain.user.mapping.UserTerm;
import EatPic.spring.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "email", length = 255, unique = true, nullable = false)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    // 유저 아이디
    @Column(name = "name_id", length = 100, nullable = false)
    @Size(min = 5, message = "5자 이상")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "영문과 숫자만")
    private String nameId;

    // 유저 닉네임
    @Column(name = "nickname", length = 100, nullable = false)
    private String nickname;

    // 소셜 로그인 종류
    @Enumerated(EnumType.STRING)
    @Column(name = "social_type", nullable = true)
    private SocialType socialType;

    @Column(name = "refresh_token", length = 255)
    private String refreshToken;

    @Column(name = "profile_image_url", length = 500)
    private String profileImageUrl;

    // 자기소개
    @Column(name = "introduce", length = 500)
    private String introduce;

    // 활성화/비활성화/탈퇴 여부
    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false)
    private UserStatus userStatus;

    @OneToMany(mappedBy = "user")
    private List<UserTerm> userTerms = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserBadge> userBadges = new ArrayList<>();

}
