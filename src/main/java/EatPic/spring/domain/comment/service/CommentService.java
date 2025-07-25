package EatPic.spring.domain.comment.service;

import EatPic.spring.domain.comment.dto.CommentRequestDTO;
import EatPic.spring.domain.comment.dto.CommentResponseDTO;
import EatPic.spring.domain.comment.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment writeComment(CommentRequestDTO.WriteCommentDto commentDto, Long cardId);
    CommentResponseDTO.commentListDTO getComments(Long cardId, int size, Long cursor);
    CommentResponseDTO.commentListDTO getReplies(Long commentId, int size, Long cursor);
    CommentResponseDTO.DeleteCommentResponseDTO deleteComments(Long commentId);
}
