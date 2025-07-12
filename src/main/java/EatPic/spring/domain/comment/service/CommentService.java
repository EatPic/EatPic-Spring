package EatPic.spring.domain.comment.service;

import EatPic.spring.domain.comment.dto.CommentRequestDTO;
import EatPic.spring.domain.comment.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment writeComment(CommentRequestDTO.WriteCommentDto commentDto, Long cardId);
    List<Comment> getCommentList(Long cardId);
}
