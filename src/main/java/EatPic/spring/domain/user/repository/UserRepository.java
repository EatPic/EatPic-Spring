package EatPic.spring.domain.user.repository;

import EatPic.spring.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByNameId(String nameId);

    Optional<User> findByEmail(String email);

    User findUserById(Long id);

    @Query("""
        SELECT u FROM User u
        WHERE (u.nameId LIKE %:query%)
          AND (:cursor IS NULL OR u.id > :cursor)
        ORDER BY u.id ASC
    """)
    Slice<User> searchAccountInAll(@Param("query") String query,
                                   @Param("cursor") Long cursor, Pageable pageable);

    @Query("""
    SELECT u
    FROM User u
    JOIN UserFollow uf ON u.id = uf.targetUser.id
    WHERE uf.user.id = :loginUserId
      AND (:cursor IS NULL OR u.id > :cursor)
      AND u.nickname LIKE %:query%
    ORDER BY u.id ASC
""")
    Slice<User> searchAccountInFollow(@Param("query") String query,
                                      @Param("cursor") Long cursor, Pageable pageable, @Param("loginUserId") Long userId);

}