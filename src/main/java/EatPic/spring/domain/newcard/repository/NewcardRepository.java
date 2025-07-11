package EatPic.spring.domain.newcard.repository;

import EatPic.spring.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewcardRepository extends JpaRepository<Card, Long> {
}
