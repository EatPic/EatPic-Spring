package EatPic.spring.domain.card.repository;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.entity.Meal;
import EatPic.spring.domain.card.mapping.CardHashtag;
import EatPic.spring.domain.user.entity.User;
import java.time.LocalDate;
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
  List<Card> findAllByUserAndIsDeletedFalseAndCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end);

@Query("""
SELECT DISTINCT c
FROM Card c
WHERE c.isDeleted = false
  AND c.isShared = true
  AND c.user.id <> :loginUserId
  AND c.user.id NOT IN (
      SELECT ub.blockedUser.id
      FROM UserBlock ub
      WHERE ub.user.id = :loginUserId
  )
  AND (:cursor IS NULL OR c.id < :cursor)
ORDER BY c.id DESC
""")
  Slice<Card> findFeedExcludeBlocked(
          @Param("loginUserId") Long loginUserId,
          @Param("cursor") Long cursor,
          Pageable pageable
  );

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

  @Query("""
    SELECT c FROM CardHashtag ch
    JOIN ch.card c
    JOIN ch.hashtag h
    WHERE h.id = :hashtagId
      AND (:cursor IS NULL OR c.id > :cursor)
      AND c.isDeleted = false
      AND c.isShared = true
    ORDER BY c.id ASC
""")
  Slice<Card> findCardsByHashtag(
          @Param("hashtagId") Long hashtagId,
          @Param("cursor") Long cursor,
          Pageable pageable
  );

  @Query("""
    SELECT COUNT(c)
    FROM CardHashtag ch
    JOIN ch.card c
    WHERE ch.hashtag.id = :hashtagId
      AND c.isDeleted = false
      AND c.isShared = true
""")
  Long countCardsByHashtag(@Param("hashtagId") Long hashtagId);

  @Query("""
    SELECT ch.hashtag.id, COUNT(ch.card.id)
    FROM CardHashtag ch
    WHERE ch.hashtag.id IN :hashtagIds
      AND ch.card.isDeleted = false
      AND ch.card.isShared = true
    GROUP BY ch.hashtag.id
    """)
  List<Object[]> countCardsByHashtagIds(@Param("hashtagIds") List<Long> hashtagIds);

  Slice<Card> findByUserIdAndIsSharedTrueOrderByIdDesc(Long userId, Pageable pageable);
  Slice<Card> findByUserIdAndIsSharedTrueAndIdLessThanOrderByIdDesc(Long userId, Long cursor, Pageable pageable);

  @Query("""
  select count(c)
  from Card c
  where c.isDeleted = false
    and c.isShared = true
    and c.user.id = :userId
""")
  Long countByUserIdAndIsDeletedFalseAndIsSharedTrue(@Param("userId") Long userId);


}