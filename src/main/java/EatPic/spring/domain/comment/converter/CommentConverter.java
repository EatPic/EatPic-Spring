package EatPic.spring.domain.comment.converter;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.comment.dto.CommentRequestDTO;
import EatPic.spring.domain.comment.dto.CommentResponseDTO;
import EatPic.spring.domain.comment.entity.Comment;
import EatPic.spring.domain.user.entity.User;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class CommentConverter {
    public static Comment WriteCommentDtoToComment(CommentRequestDTO.WriteCommentDto writeCommentDto, Card card, User user) {
        return Comment.builder()
                .card(card)
                .user(user)
                .content(writeCommentDto.getContent())
                .build();
    }

    public static CommentResponseDTO.WriteCommentResponseDTO CommentToWriteCommentResponseDTO(Comment comment){
        return CommentResponseDTO.WriteCommentResponseDTO.builder()
                .commentId(comment.getId())
                .parentCommentId(comment.getParentComment()==null?null:comment.getParentComment().getId())
                .cardId(comment.getCard().getId())
                .content(comment.getContent())
                .build();
    }

    public static CommentResponseDTO.CommentDTO CommentToCommentDTO(Comment comment){
        return CommentResponseDTO.CommentDTO.builder()
                .parentCommentId(comment.getParentComment()==null?null:comment.getParentComment().getId())
                .commentId(comment.getId())
                .nickname(comment.getUser().getNickname())
                .nameId(comment.getUser().getNameId())
                .content(comment.getContent())
                .createdAt(comment.getUpdatedAt().toString())
                .build();
    }

    public static CommentResponseDTO.commentListDTO CommentPageToCommentListResponseDTO(Long cardId, Page<Comment> commentList){
        return CommentResponseDTO.commentListDTO.builder()
                .total((int)commentList.getTotalElements())
                .page(commentList.getNumber() + 1)
                .size(commentList.getSize())
                .cardId(cardId)
                .commentList(commentList.getContent().stream().map(CommentConverter::CommentToCommentDTO).collect(Collectors.toList()))
                .build();
    }
}
