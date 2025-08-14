package EatPic.spring.domain.user.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckEmailResponseDTO {
    @NotNull
    private String email;
    @NotNull
    private boolean isDuplicate;
}
