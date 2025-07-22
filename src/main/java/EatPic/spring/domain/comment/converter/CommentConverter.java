package EatPic.spring.domain.comment.converter;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.comment.dto.CommentRequestDTO;
import EatPic.spring.domain.comment.dto.CommentResponseDTO;
import EatPic.spring.domain.comment.entity.Comment;
import EatPic.spring.domain.user.entity.User;
import org.springframework.data.domain.Slice;

import java.util.List;

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

    public static CommentResponseDTO.commentListDTO CommentSliceToCommentListResponseDTO(Slice<Comment> commentList){
        List<CommentResponseDTO.CommentDTO> commentDTOList = commentList.getContent().stream().map(CommentConverter::CommentToCommentDTO).toList();
        return CommentResponseDTO.commentListDTO.builder()
                .hasNext(commentList.hasNext())
                .nextCursor(commentList.hasNext()?commentDTOList.get(commentDTOList.size()-1).getCommentId():null)
                .commentList(commentDTOList)
                .build();
    }

    public static CommentResponseDTO.DeleteCommentResponseDTO CommentIdListToDeleteCommentResponseDTO(List<Long> commentIds){
        return CommentResponseDTO.DeleteCommentResponseDTO.builder()
                .deletedCommentIds(commentIds)
                .total(commentIds.size())
                .build();

    }
}
