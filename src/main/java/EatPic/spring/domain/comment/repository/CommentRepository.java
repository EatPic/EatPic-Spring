package EatPic.spring.domain.comment.repository;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentById(Long cardId);
    List<Comment> findAllByParentComment(Comment comment);
    Page<Comment> findAllByCard(Card card, Pageable pageable);

    // 댓글 개수 세기
    Long countAllCommentByCard(Card card);
}
