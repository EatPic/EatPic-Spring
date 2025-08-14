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
public class CheckNicknameResponseDTO {
    @NotNull
    private String nickname;
    @NotNull
    private boolean isDuplicate;
}
