package EatPic.spring.domain.user.repository;

import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.mapping.UserBadge;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Integer> {

  // 특정 유저의 모든 유저뱃지 가져오기
  List<UserBadge> findByUser(User user);
}
