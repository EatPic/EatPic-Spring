package EatPic.spring.domain.comment.service;

import EatPic.spring.domain.comment.dto.CommentRequestDTO;
import EatPic.spring.domain.comment.dto.CommentResponseDTO;
import EatPic.spring.domain.comment.entity.Comment;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface CommentService {
    Comment writeComment(HttpServletRequest request, CommentRequestDTO.WriteCommentDto commentDto, Long cardId);
    CommentResponseDTO.commentListDTO getComments(Long cardId, int size, Long cursor);
    CommentResponseDTO.commentListDTO getReplies(Long commentId, int size, Long cursor);
    CommentResponseDTO.DeleteCommentResponseDTO deleteComments(HttpServletRequest request,Long commentId);
}
