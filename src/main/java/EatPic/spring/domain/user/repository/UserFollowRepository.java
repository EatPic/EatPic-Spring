package EatPic.spring.domain.user.repository;

import EatPic.spring.domain.user.mapping.*;
import EatPic.spring.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFollowRepository extends JpaRepository<UserFollow,Long> {
    List<UserFollow> findByUser(User user);
}
