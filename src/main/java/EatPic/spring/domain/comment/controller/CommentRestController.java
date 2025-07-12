package EatPic.spring.domain.comment.controller;

import EatPic.spring.domain.comment.converter.CommentConverter;
import EatPic.spring.domain.comment.dto.CommentRequestDTO;
import EatPic.spring.domain.comment.dto.CommentResponseDTO;
import EatPic.spring.domain.comment.entity.Comment;
import EatPic.spring.domain.comment.repository.CommentRepository;
import EatPic.spring.domain.comment.service.CommentServiceImpl;
import EatPic.spring.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommentRestController {
    private final CommentServiceImpl commentService;
    private final CommentRepository commentRepository;

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
            description = "parent_comment_id가 null이면 댓글, non-null이면 답글 입니다."+"\n"+ "페이지는 1부터 시작합니다.")
    @GetMapping("/cards/{cardId}/comments")
    public BaseResponse<CommentResponseDTO.commentListDTO> writeComment(@PathVariable("cardId") Long cardId,
                                                                       @RequestParam(defaultValue = "1") int page,
                                                                       @RequestParam(defaultValue = "15") int size) {

        List<Comment> commentList = commentService.getCommentList(cardId);
        List<CommentResponseDTO.CommentDTO> commentDTOList = commentList.stream().map(CommentConverter::CommentToCommentDTO).toList();
        int total = commentDTOList.size();

        //페이징
        int fromIndex = Math.max(0, (page - 1) * size);
        int toIndex = Math.min(fromIndex + size, total);
        List<CommentResponseDTO.CommentDTO> pagedCommentDTOList = commentDTOList.subList(fromIndex,toIndex);

        CommentResponseDTO.commentListDTO result = CommentConverter.CommentDTOListToCommentListResponseDTO(pagedCommentDTOList,cardId,total,page,size);
        return BaseResponse.onSuccess(result);
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