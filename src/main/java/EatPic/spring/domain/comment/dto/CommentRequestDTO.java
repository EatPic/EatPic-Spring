package EatPic.spring.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentRequestDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WriteCommentDto{
        private Long parentCommentId;

        @NotBlank(message = "내용은 필수입니다.")
        @Size(max = 255, message = "댓글은 255자 이하로 입력해주세요.")
        private String content;
    }
}
