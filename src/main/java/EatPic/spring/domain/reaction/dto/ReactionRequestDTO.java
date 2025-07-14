package EatPic.spring.domain.reaction.dto;

import EatPic.spring.domain.reaction.entity.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class ReactionRequestDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReactionAddRequestDto{
        private ReactionType reactionType;
    }
}
