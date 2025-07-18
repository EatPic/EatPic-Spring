package EatPic.spring.domain.comment.repository;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.comment.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

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
    Long countAllByCard(Card card);

}
