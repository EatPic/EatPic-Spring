package EatPic.spring.domain.user.repository;

import EatPic.spring.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByNameId(String nameId);

    Optional<User> findByEmail(String email);

    User findUserById(Long id);
}
