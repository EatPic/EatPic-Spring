package EatPic.spring.domain.card.repository;

import EatPic.spring.domain.card.entity.Card;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchRepository extends JpaRepository<Card, Long> {
    // 커서 페이징으로 전체 카드 조회
    @Query("SELECT c FROM Card c WHERE c.id > :cursor ORDER BY c.id ASC")
    List<Card> findByCursor(@Param("cursor") Long cursor, Pageable pageable);
}
