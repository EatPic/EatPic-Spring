package EatPic.spring.domain.comment.controller;

import EatPic.spring.domain.comment.converter.CommentConverter;
import EatPic.spring.domain.comment.dto.CommentRequestDTO;
import EatPic.spring.domain.comment.dto.CommentResponseDTO;
import EatPic.spring.domain.comment.entity.Comment;
import EatPic.spring.domain.comment.service.CommentService;
import EatPic.spring.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
@Tag(name = "Comment", description = "댓글 관련 API")
public class CommentRestController {
    private final CommentService commentService;

    @Operation(
            summary = "카드 댓글 작성",
            description = "parent_comment_id가 null이면 댓글, non-null이면 답글 입니다.")
    @PostMapping("/cards/{cardId}/comments")
    public BaseResponse<CommentResponseDTO.WriteCommentResponseDTO> writeComment(@PathVariable("cardId") Long cardId,
                                                                             @RequestBody CommentRequestDTO.WriteCommentDto requestDto) {
        Comment comment = commentService.writeComment(requestDto,cardId);

        return BaseResponse.onSuccess(CommentConverter.CommentToWriteCommentResponseDTO(comment));
    }

    @Operation(
            summary = "카드 댓글 조회",
            description = "hasNext가 null이면 마지막 페이지 입니다.")
    @GetMapping("/cards/{cardId}/comments")
    public BaseResponse<CommentResponseDTO.commentListDTO> getComment(@PathVariable("cardId") Long cardId,
                                                                       @RequestParam(required = false) Long cursor,
                                                                       @RequestParam(defaultValue = "15") int size) {

        return BaseResponse.onSuccess(commentService.getComments(cardId, size, cursor));
    }

    @Operation(
            summary = "답글 조회",
            description = "해당 댓글의 답글을 조회합니다. hasNext가 null이면 마지막 페이지 입니다.")
    @GetMapping("/cards/{cardId}/comments/{commentId}/replies")
    public BaseResponse<CommentResponseDTO.commentListDTO> getReplies(@PathVariable("commentId") Long commentId,
                                                                        @RequestParam(required = false) Long cursor,
                                                                        @RequestParam(defaultValue = "15") int size) {

        return BaseResponse.onSuccess(commentService.getReplies(commentId, size, cursor));
    }

    @Operation(
            summary = "카드 댓글 삭제",
            description = "댓글이면 답글까지 전체 삭제, 답글이면 해당 답글만 삭제합니다.")
    @DeleteMapping("/cards/{cardId}/comments/{commentId}")
    public BaseResponse<CommentResponseDTO.DeleteCommentResponseDTO> deleteComment(@PathVariable("cardId") Long cardId,
                                                                        @PathVariable("commentId") Long commentId) {

        List<Long> deletedList = commentService.deleteComments(commentId);

        CommentResponseDTO.DeleteCommentResponseDTO result =
                CommentResponseDTO.DeleteCommentResponseDTO.builder()
                        .total(deletedList.size())
                        .deletedCommentIds(deletedList)
                        .build();

        return BaseResponse.onSuccess(result);
    }
}