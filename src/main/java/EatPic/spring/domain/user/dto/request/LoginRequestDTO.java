package EatPic.spring.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "이메일을 임력해 주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotNull
    private String email;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @NotNull
    private String password;
}
