package EatPic.spring.domain.uuid.repository;

import EatPic.spring.domain.uuid.entity.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UuidRepository extends JpaRepository<Uuid,Long> {
}
