package EatPic.spring.domain.comment.dto;

import EatPic.spring.domain.comment.entity.Comment;
import lombok.*;

import java.util.Date;
import java.util.List;

public class CommentResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WriteCommentResponseDTO{
        private Long commentId;
        private Long parentCommentId;
        private Long cardId;
        private Long userId;
        private String content;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentDTO{
        private Long parentCommentId;
        private Long commentId;
        private String nickname;
        private String nameId;
        private String content;
        private String createdAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class commentListDTO{
        private int total;
        private int page;
        private int size;
        private Long cardId;
        private List<CommentDTO> commentList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteCommentResponseDTO{
        private int total;
        private List<Long> deletedCommentIds;
    }
}
