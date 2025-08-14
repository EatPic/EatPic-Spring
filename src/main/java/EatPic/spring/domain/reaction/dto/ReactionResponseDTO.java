package EatPic.spring.domain.reaction.dto;

import EatPic.spring.domain.reaction.entity.ReactionType;
import EatPic.spring.domain.user.dto.response.UserResponseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ReactionResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReactionHandleResponseDto{
        @NotNull
        private long cardId;
        @NotNull
        private long userId;
        @NotNull
        ReactionType reactionType;
        @NotNull
        private String status;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CardReactionUserListDto{
        @NotNull
        private long cardId;
        @NotNull
        private ReactionType reactionType;
        @NotNull
        private int page;
        @NotNull
        private int size;
        @NotNull
        private int total;

        List<UserResponseDTO.ProfileDto> userList;
    }
}
