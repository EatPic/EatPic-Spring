package EatPic.spring.domain.bookmark.dto;

import EatPic.spring.domain.reaction.entity.ReactionType;
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
        private long cardId;
        private long userId;
        private String status;
    }
}
