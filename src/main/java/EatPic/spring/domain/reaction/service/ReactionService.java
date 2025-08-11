package EatPic.spring.domain.reaction.service;

import EatPic.spring.domain.reaction.dto.ReactionResponseDTO;
import EatPic.spring.domain.reaction.entity.Reaction;
import EatPic.spring.domain.reaction.entity.ReactionType;
import jakarta.servlet.http.HttpServletRequest;

import java.net.http.HttpRequest;

public interface ReactionService {
    ReactionResponseDTO.ReactionHandleResponseDto handleReaction(HttpServletRequest request, Long cardId, ReactionType reactionType);
    ReactionResponseDTO.CardReactionUserListDto getCardUsersByReactionType(HttpServletRequest request,Long cardId, ReactionType reactionType, Integer page, Integer size);
}
