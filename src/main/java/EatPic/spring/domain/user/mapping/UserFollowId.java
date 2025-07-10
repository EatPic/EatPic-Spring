package EatPic.spring.domain.user.mapping;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserFollowId implements Serializable {
    private Long user;
    private Long targetUser;
}