package EatPic.spring.domain.user.repository;

import EatPic.spring.domain.user.mapping.UserBlock;
import EatPic.spring.domain.user.mapping.UserBlockId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBlockRepository extends JpaRepository<UserBlock, UserBlockId> {
}
