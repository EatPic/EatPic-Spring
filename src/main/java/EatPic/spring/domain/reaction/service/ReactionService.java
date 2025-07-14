package EatPic.spring.domain.reaction.service;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.reaction.entity.Reaction;
import EatPic.spring.domain.reaction.entity.ReactionType;

public interface ReactionService {
    Reaction handleReaction(Long cardId, ReactionType reactionType);
}
