package EatPic.spring.domain.reaction.entity;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ReactionId implements Serializable {
    private Long card;
    private Long user;
}