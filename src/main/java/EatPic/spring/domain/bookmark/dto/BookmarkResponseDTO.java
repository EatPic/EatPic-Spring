package EatPic.spring.domain.bookmark.dto;

import EatPic.spring.domain.reaction.entity.ReactionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BookmarkResponseDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookmarkResponseDto{
        @NotNull
        private long cardId;
        @NotNull
        private long userId;
        @NotNull
        private String status;
    }
}
