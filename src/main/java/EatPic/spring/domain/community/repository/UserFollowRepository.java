package EatPic.spring.domain.community.repository;

import EatPic.spring.domain.community.UserFollow;
import EatPic.spring.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFollowRepository extends JpaRepository<UserFollow,Long> {
    List<UserFollow> findByUser(User user);
}
