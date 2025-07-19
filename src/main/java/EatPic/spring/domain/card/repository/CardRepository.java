package EatPic.spring.domain.card.repository;

import EatPic.spring.domain.card.entity.Card;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<Card, Long> {
  Card findCardById(Long cardId);
  List<Card> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

  // 커서 페이징으로 전체 카드 조회
  @Query("SELECT c FROM Card c WHERE c.id > :cursor ORDER BY c.id ASC")
  List<Card> findByCursor(@Param("cursor") Long cursor, Pageable pageable);
}
