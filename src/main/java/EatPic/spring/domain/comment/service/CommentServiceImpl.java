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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public final Comment writeComment(CommentRequestDTO.WriteCommentDto writeCommentDto, Long cardId) {
        // 작성자
        User user = userRepository.findUserById(1L); //todo: 로그인한 사용자로 수정
        // 카드(피드)
        Card card = cardRepository.findCardById(cardId);

        Comment comment = CommentConverter.WriteCommentDtoToComment(writeCommentDto,card,user);
        commentRepository.save(comment);

        return comment;
    }

    @Override
    public List<Comment> getCommentList(Long cardId) {
        Card card = cardRepository.findCardById(cardId);
        return commentRepository.findAllByCard(card);
    }

    @Override
    public List<Long> deleteComments(Long commentId) {

        Comment comment = commentRepository.findCommentById(commentId);
        List<Comment> childComments = commentRepository.findAllByParentComment(comment);

        List<Long> deletedCommentIds = new ArrayList<>();
        deletedCommentIds.add(comment.getId());
        for(Comment childComment : childComments){
            deletedCommentIds.add(childComment.getId());
        }

        commentRepository.delete(comment);
        commentRepository.deleteAll(childComments);

        return deletedCommentIds;
    }
}
