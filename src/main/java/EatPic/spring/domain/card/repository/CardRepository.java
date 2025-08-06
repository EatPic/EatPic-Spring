package EatPic.spring.domain.card.repository;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.entity.Meal;
import EatPic.spring.domain.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;

import EatPic.spring.domain.card.entity.Meal;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<Card, Long> {
  Card findCardById(Long cardId);
  List<Card> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

  // 커서 페이징으로 전체 카드 조회
  @Query("""
    SELECT c FROM Card c
    WHERE (:cursor IS NULL OR c.id > :cursor)
    ORDER BY c.id ASC
""")
  Slice<Card> findByCursor(@Param("cursor") Long cursor, Pageable pageable);
  boolean existsByUserIdAndMealAndCreatedAtBetween(Long userId, Meal meal, LocalDateTime start, LocalDateTime end);

  Optional<Card> findByIdAndIsDeletedFalse(Long id);

  List<Card> findAllByUserAndCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end);

  Slice<Card> findByIsDeletedFalseAndIsSharedTrueOrderByIdDesc(Pageable pageable);
  Slice<Card> findByIsDeletedFalseAndIsSharedTrueAndIdLessThanOrderByIdDesc(Long cursor, Pageable pageable);

  Slice<Card> findByIsDeletedFalseAndUserIdOrderByIdDesc(Long userId, Pageable pageable);
  Slice<Card> findByIsDeletedFalseAndIsSharedTrueAndUserIdAndIdLessThanOrderByIdDesc(Long userId, Long cursor, Pageable pageable);

  Slice<Card> findByIsDeletedFalseAndUserIdAndCreatedAtAfterOrderByIdDesc(Long userId, LocalDateTime sevenDaysAgo, Pageable pageable);
  Slice<Card> findByIsDeletedFalseAndUserIdAndCreatedAtAfterAndIdLessThanOrderByIdDesc(Long userId, LocalDateTime sevenDaysAgo, Long cursor, Pageable pageable);

  boolean existsByUserAndCreatedAtBetweenAndMeal(User user, LocalDateTime start, LocalDateTime end, Meal meal);

  boolean existsByUserAndCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end);

  List<Card> findByUser(User user);

  @Query("""
    SELECT c FROM Card c
    JOIN Reaction r ON r.card = c
    GROUP BY c
    HAVING COUNT(r) >= 1
""")
  List<Card> findCardsWithReactionCountOver1();  //초기 테스트로 1개로 수정 (기존은 100개)
}