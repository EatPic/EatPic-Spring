package EatPic.spring.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckEmailResponseDTO {
    private String email;
    private boolean isDuplicate;
}
