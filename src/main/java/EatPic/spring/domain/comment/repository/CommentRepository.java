package EatPic.spring.domain.comment.repository;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentById(Long cardId);
    List<Comment> findAllByCard(Card card);
}
