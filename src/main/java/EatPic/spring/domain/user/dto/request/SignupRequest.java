package EatPic.spring.domain.user.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, message = "6자리 이상의 비밀번호")
    private String password;

    @NotBlank
    @Size(min = 5)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "2자 이상(한글만 가능)")
    private String nameId;

    @NotBlank
    @Size(min = 2)
    @Pattern(regexp = "^[가-힣]+$", message = "5자 이상(영문, 숫자 사용 가능)")
    private String nickname;

    private Boolean marketingAgree;
}
