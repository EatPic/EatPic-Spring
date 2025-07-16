package EatPic.spring.domain.bookmark.entity;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BookmarkId implements Serializable {
    private Long user;
    private Long card;
}
