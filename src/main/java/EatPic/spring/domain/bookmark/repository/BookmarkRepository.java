package EatPic.spring.domain.bookmark.repository;

import EatPic.spring.domain.bookmark.entity.Bookmark;
import EatPic.spring.domain.bookmark.entity.BookmarkId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, BookmarkId> {

  boolean existsByCardIdAndUserId(Long cardId, Long userId);
}
