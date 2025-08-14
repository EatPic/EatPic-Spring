package EatPic.spring.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    @NotNull
    String email;
    @NotNull
    String nameId;
    @NotNull
    String nickName;
}
