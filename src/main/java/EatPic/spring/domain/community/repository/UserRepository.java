package EatPic.spring.domain.community.repository;

import EatPic.spring.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserById(Long id);
}
