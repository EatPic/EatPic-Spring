package EatPic.spring.domain.card.repository;

import EatPic.spring.domain.hashtag.entity.Hashtag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findByHashtagName(String hashtagName);

    @Query("""
    SELECT h
    FROM Hashtag h
    WHERE h.hashtagName LIKE :query
      AND (:cursor IS NULL OR h.id > :cursor)
    ORDER BY h.id ASC
""")
    Slice<Hashtag> searchHashtagInAll(@Param("query") String query,
                                      @Param("cursor") Long cursor,
                                      Pageable pageable);

    @Query("""
    SELECT DISTINCT h
    FROM CardHashtag ch
    JOIN ch.card c
    JOIN ch.hashtag h
    WHERE c.user.id IN :followingUserIds
      AND h.hashtagName LIKE %:query%
      AND (:cursor IS NULL OR h.id > :cursor)
      AND c.isDeleted = false
      AND c.isShared = true
    ORDER BY h.id ASC
""")
    Slice<Hashtag> searchHashtagInFollow(
            @Param("query") String query,
            @Param("followingUserIds") List<Long> followingUserIds,
            @Param("cursor") Long cursor,
            Pageable pageable
    );

}