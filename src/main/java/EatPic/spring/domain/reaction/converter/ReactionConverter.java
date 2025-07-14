package EatPic.spring.domain.reaction.converter;

import EatPic.spring.domain.reaction.dto.ReactionResponseDTO;
import EatPic.spring.domain.reaction.entity.Reaction;

public class ReactionConverter {
    public static ReactionResponseDTO.ReactionHandleResponseDto reactionToReactionHandleResponseDTO(Reaction reaction) {
        return ReactionResponseDTO.ReactionHandleResponseDto.builder()
                .cardId(reaction.getCard().getId())
                .userId(reaction.getUser().getId())
                .reactionType(reaction.getReactionType())
                .build();
    }
}
