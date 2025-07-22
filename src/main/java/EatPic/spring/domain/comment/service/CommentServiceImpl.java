package EatPic.spring.domain.comment.service;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.comment.converter.CommentConverter;
import EatPic.spring.domain.comment.dto.CommentRequestDTO;
import EatPic.spring.domain.comment.dto.CommentResponseDTO;
import EatPic.spring.domain.comment.entity.Comment;
import EatPic.spring.domain.comment.repository.CommentRepository;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.global.common.code.status.ErrorStatus;
import EatPic.spring.global.common.exception.GeneralException;
import EatPic.spring.global.common.exception.handler.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static EatPic.spring.global.common.code.status.ErrorStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public Comment writeComment(CommentRequestDTO.WriteCommentDto writeCommentDto, Long cardId) {
        // 작성자
        User user = userRepository.findUserById(1L);
        // 카드(피드)
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ExceptionHandler(CARD_NOT_FOUND));

        Comment comment = CommentConverter.WriteCommentDtoToComment(writeCommentDto,card,user);
        commentRepository.save(comment);

        return comment;
    }

    @Override
    public CommentResponseDTO.commentListDTO getComments(Long cardId, int size, Long cursor) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(()-> new ExceptionHandler(CARD_NOT_FOUND));

        validateCursorExists(cursor);

        Pageable pageable = PageRequest.of(0, size, Sort.by("createdAt").ascending());

        Slice<Comment> commentSlice;
        if(cursor==null){
            commentSlice = commentRepository.findAllByCardAndParentCommentIsNull(card,pageable);
        }else{
            commentSlice = commentRepository.findAllByCardAndParentCommentIsNullAndIdGreaterThanOrderByIdAsc(card, cursor, pageable);
        }
        return CommentConverter.CommentSliceToCommentListResponseDTO(commentSlice);
    }

    @Override
    public CommentResponseDTO.commentListDTO getReplies(Long commentId, int size, Long cursor) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new GeneralException(COMMENT_NOT_FOUND));

        validateCursorExists(cursor);

        Pageable pageable = PageRequest.of(0, size, Sort.by("createdAt").ascending());
        Slice<Comment> commentSlice;
        if(cursor==null){
            commentSlice = commentRepository.findAllByParentComment(comment,pageable);
        }else{
            commentSlice = commentRepository.findAllByParentCommentAndIdGreaterThanOrderByIdAsc(comment,cursor, pageable);
        }
        return CommentConverter.CommentSliceToCommentListResponseDTO(commentSlice);
    }

    @Override
    public CommentResponseDTO.DeleteCommentResponseDTO deleteComments(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new GeneralException(COMMENT_NOT_FOUND));

        List<Comment> childComments = commentRepository.findAllByParentComment(comment);

        List<Long> deletedCommentIds = new ArrayList<>();
        deletedCommentIds.add(comment.getId());
        for (Comment childComment : childComments) {
            deletedCommentIds.add(childComment.getId());
        }

        commentRepository.delete(comment);
        commentRepository.deleteAll(childComments);

        return CommentConverter.CommentIdListToDeleteCommentResponseDTO(deletedCommentIds);
    }

    private void validateCursorExists(Long cursor) {
        if (cursor!=null && commentRepository.findById(cursor).isEmpty()) {
            throw new GeneralException(CURSOR_NOT_FOUND);
        }
    }

}
