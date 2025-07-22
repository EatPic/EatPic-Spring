package EatPic.spring.domain.user.repository;

import EatPic.spring.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email); // 회원 가입 시, 이메일 중복 검사

    boolean existsByNickname(String nickname);

    boolean existsByNameId(String nameId);

    Optional<User> findByEmail(String email); // 로그인 시, 이메일로 유저 찾기

    User findUserById(Long id);
}
