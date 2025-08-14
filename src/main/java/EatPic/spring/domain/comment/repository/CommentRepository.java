package EatPic.spring.domain.comment.repository;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.comment.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentById(Long commentId);
    List<Comment> findAllByParentComment(Comment comment);
    Slice<Comment> findAllByCardAndParentCommentIsNull(Card card, Pageable pageable);
    Slice<Comment> findAllByCardAndParentCommentIsNullAndIdGreaterThanOrderByIdAsc(
            Card card, Long cursor, Pageable pageable
    );
    Slice<Comment> findAllByParentComment(Comment comment, Pageable pageable);
    Slice<Comment> findAllByParentCommentAndIdGreaterThanOrderByIdAsc(Comment parent, Long cursor, Pageable pageable);
    Long countAllByParentComment(Comment comment);
    int countAllByCard(Card card);

    int countByCardId(Long cardId);

    @Query("SELECT c.card.id, COUNT(c) FROM Comment c WHERE c.card.id IN :cardIds GROUP BY c.card.id")
    List<Object[]> countByCardIdIn(@Param("cardIds") List<Long> cardIds);

}
