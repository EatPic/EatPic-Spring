package EatPic.spring.domain.bookmark.repository;

import EatPic.spring.domain.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

  boolean existsByCardIdAndUserId(Long cardId, Long userId);
}
