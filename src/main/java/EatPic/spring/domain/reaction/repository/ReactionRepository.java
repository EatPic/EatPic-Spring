package EatPic.spring.domain.reaction.repository;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.reaction.entity.Reaction;
import EatPic.spring.domain.reaction.entity.ReactionId;
import EatPic.spring.domain.reaction.entity.ReactionType;
import EatPic.spring.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, ReactionId> {
    Reaction findByUserAndCard(User user, Card card);
}
