package EatPic.spring.domain.card.repository;

import EatPic.spring.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card,Long> {
    Card findCardById(Long cardId);
}
