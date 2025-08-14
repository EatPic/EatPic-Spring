package EatPic.spring.domain.comment.dto;

import EatPic.spring.domain.comment.entity.Comment;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;

public class CommentResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WriteCommentResponseDTO{
        @NotNull
        private Long commentId;
        @NotNull
        private Long parentCommentId;
        @NotNull
        private Long cardId;
        @NotNull
        private Long userId;
        @NotNull
        private String content;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentDTO{
        @NotNull
        private Long parentCommentId;
        @NotNull
        private Long commentId;
        @NotNull
        private String nickname;
        @NotNull
        private String nameId;
        @NotNull
        private String content;
        @NotNull
        private String createdAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class commentListDTO{
        @NotNull
        private boolean hasNext;
        private Long nextCursor;
        @NotNull
        private List<CommentDTO> commentList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteCommentResponseDTO{
        @NotNull
        private int total;
        @NotNull
        private List<Long> deletedCommentIds;
    }
}
