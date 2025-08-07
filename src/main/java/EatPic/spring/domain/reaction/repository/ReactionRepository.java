package EatPic.spring.domain.reaction.repository;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.reaction.entity.Reaction;
import EatPic.spring.domain.reaction.entity.ReactionId;
import EatPic.spring.domain.reaction.entity.ReactionType;
import EatPic.spring.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ReactionRepository extends JpaRepository<Reaction, ReactionId> {
    Reaction findByUserAndCard(User user, Card card);

    @Query("SELECT r.user FROM Reaction r WHERE r.card.id = :cardId AND r.reactionType = :reactionType")
    Page<User> findUsersByCardAndReactionType(@Param("cardId") Long cardId,
                                              @Param("reactionType") ReactionType reactionType,
                                              Pageable pageable);

    // 반응 개수 세기
    Long countAllReactionByCard(Card card);

    Optional<Reaction> findByCardIdAndUserId(Long cardId, Long userId);
    int countByCardId(Long cardId);

    long countByCardAndReactionType(Card card, ReactionType reactionType);


}
