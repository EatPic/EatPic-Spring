package EatPic.spring.domain.reaction.service;

import EatPic.spring.domain.reaction.dto.ReactionResponseDTO;
import EatPic.spring.domain.reaction.entity.Reaction;
import EatPic.spring.domain.reaction.entity.ReactionType;

public interface ReactionService {
    Reaction handleReaction(Long cardId, ReactionType reactionType);
    ReactionResponseDTO.CardReactionUserListDto getCardUsersByReactionType(Long cardId, ReactionType reactionType, Integer page, Integer size);
}
