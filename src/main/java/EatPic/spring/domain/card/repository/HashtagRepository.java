package EatPic.spring.domain.card.repository;

import EatPic.spring.domain.hashtag.entity.Hashtag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}