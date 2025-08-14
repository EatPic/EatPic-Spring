package EatPic.spring.domain.bookmark.repository;

import EatPic.spring.domain.bookmark.entity.Bookmark;
import EatPic.spring.domain.bookmark.entity.BookmarkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface BookmarkRepository extends JpaRepository<Bookmark, BookmarkId> {

  boolean existsByCardIdAndUserId(Long cardId, Long userId);

  @Query("SELECT b.card.id FROM Bookmark b WHERE b.user.id = :userId AND b.card.id IN :cardIds")
  Set<Long> findCardIdsByUserIdAndCardIdIn(@Param("userId") Long userId, @Param("cardIds") List<Long> cardIds);

}
