package EatPic.spring.domain.user.repository;

import EatPic.spring.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email); // 회원 가입 시, 이메일 중복 검사

    boolean existsByNickname(String nickname);

    boolean existsByNameId(String nameId);

    Optional<User> findByEmail(String email); // 로그인 시, 이메일로 유저 찾기

    User findUserById(Long id);

    @Query("""
    SELECT u
    FROM User u
    WHERE (LOWER(u.nameId) LIKE LOWER(CONCAT(:query, '%'))
           OR LOWER(u.nickname) LIKE LOWER(CONCAT(:query, '%')))
      AND (:cursor IS NULL OR u.id > :cursor)
    ORDER BY u.id ASC
""")
    Slice<User> searchAccountInAll(@Param("query") String query,
                                   @Param("cursor") Long cursor,
                                   Pageable pageable);

    @Query("""
    SELECT u
    FROM User u
    JOIN UserFollow uf ON u.id = uf.targetUser.id
    WHERE uf.user.id = :loginUserId
      AND (:cursor IS NULL OR u.id > :cursor)
      AND (:query IS NULL OR 
        LOWER(u.nameId) LIKE LOWER(CONCAT(:query, '%'))
        OR LOWER(u.nickname) LIKE LOWER(CONCAT(:query, '%')))
    ORDER BY u.id ASC
""")
    Slice<User> searchAccountInFollow(@Param("query") String query,
                                      @Param("cursor") Long cursor,
                                      Pageable pageable,
                                      @Param("loginUserId") Long userId);

    @Query("""
    SELECT u
    FROM User u
    JOIN UserFollow uf ON u.id = uf.user.id
    WHERE uf.targetUser.id = :loginUserId
      AND (:cursor IS NULL OR u.id > :cursor)
      AND (:query IS NULL OR
       LOWER(u.nameId) LIKE LOWER(CONCAT(:query, '%'))
       OR LOWER(u.nickname) LIKE LOWER(CONCAT(:query, '%')))
    ORDER BY u.id ASC
""")
    Slice<User> searchAccountInFollower(@Param("query") String query,
                                      @Param("cursor") Long cursor,
                                      Pageable pageable,
                                      @Param("loginUserId") Long userId);


//    @Query("""
//    SELECT u FROM User u
//    WHERE (u.nameId LIKE %:query% OR u.nickname LIKE %:query%)
//      AND (:cursor IS NULL OR u.id > :cursor)
//    ORDER BY u.id ASC
//""")
//    Slice<User> searchAccountInAll(@Param("query") String query,
//                                   @Param("cursor") Long cursor, Pageable pageable);
//
//
//    @Query("""
//    SELECT u
//    FROM User u
//    JOIN UserFollow uf ON u.id = uf.targetUser.id
//    WHERE uf.user.id = :loginUserId
//      AND (:cursor IS NULL OR u.id > :cursor)
//      AND (u.nameId LIKE %:query% OR u.nickname LIKE %:query%)
//    ORDER BY u.id ASC
//""")
//    Slice<User> searchAccountInFollow(@Param("query") String query,
//                                      @Param("cursor") Long cursor,
//                                      Pageable pageable,
//                                      @Param("loginUserId") Long userId);



}