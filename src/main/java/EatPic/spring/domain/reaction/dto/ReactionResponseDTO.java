package EatPic.spring.domain.reaction.dto;

import EatPic.spring.domain.reaction.entity.ReactionType;
import EatPic.spring.domain.user.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

public class ReactionResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReactionHandleResponseDto{
        private long cardId;
        private long userId;
        ReactionType reactionType;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CardReactionUserListDto{
        private long cardId;
        private ReactionType reactionType;
        private int page;
        private int size;
        private int total;

        List<UserResponseDTO.ProfileDto> userList;
    }
}
