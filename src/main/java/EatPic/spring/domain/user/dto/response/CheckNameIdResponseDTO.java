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
public class CheckNameIdResponseDTO {
    @NotNull
    private String nameId;
    @NotNull
    private boolean isDuplicate;
}
