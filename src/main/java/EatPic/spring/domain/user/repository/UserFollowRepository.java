package EatPic.spring.domain.user.repository;

import EatPic.spring.domain.user.mapping.*;
import EatPic.spring.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserFollowRepository extends JpaRepository<UserFollow,Long> {
    Page<UserFollow> findByUser(User user, Pageable pageable);
    Boolean existsByUserAndTargetUser(User user, User targetUser);

     @Query("""
        SELECT uf.targetUser.id
        FROM UserFollow uf
        WHERE uf.user.id = :userId
    """)
    List<Long> findFollowingUserIds(@Param("userId") Long userId);
}
