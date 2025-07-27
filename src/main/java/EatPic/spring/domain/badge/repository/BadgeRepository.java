package EatPic.spring.domain.badge.repository;

import EatPic.spring.domain.badge.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Integer> {

}
