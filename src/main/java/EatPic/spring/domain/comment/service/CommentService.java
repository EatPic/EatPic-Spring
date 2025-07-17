package EatPic.spring.domain.comment.service;

import EatPic.spring.domain.comment.dto.CommentRequestDTO;
import EatPic.spring.domain.comment.dto.CommentResponseDTO;
import EatPic.spring.domain.comment.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment writeComment(CommentRequestDTO.WriteCommentDto commentDto, Long cardId);
    CommentResponseDTO.commentListDTO getCommentList(Long cardId, int page, int size);
    List<Long> deleteComments(Long commentId);
}
