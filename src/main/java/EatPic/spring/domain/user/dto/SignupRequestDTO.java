package EatPic.spring.domain.user.dto;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, message = "6자리 이상의 비밀번호")
    private String password;

    @NotBlank(message = "다시 한 번 입력해 주세요.")
    private String passwordConfirm;

    @AssertTrue(message = "비밀번호가 일치하지 않습니다.")
    public boolean isPasswordMatching() {
        return password != null && password.equals(passwordConfirm); // password : Not Null
    }

    @NotBlank
    @Size(min = 5)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "5자 이상(영문, 숫자 사용 가능)")
    private String nameId;

    @NotBlank
    @Size(min = 2)
    @Pattern(regexp = "^[가-힣]+$", message = "2자 이상(한글만 가능)")
    private String nickname;

    @NotNull(message = "이용약관 동의는 필수입니다.")
    @AssertTrue(message = "이용약관에 동의해야 합니다.")
    private Boolean termsAgreed;               // (필수) 이용약관

    @NotNull(message = "개인정보 처리방침 동의는 필수입니다.")
    @AssertTrue(message = "개인정보 처리방침에 동의해야 합니다.")
    private Boolean privacyPolicyAgreed;       // (필수) 개인정보 처리방침

    private Boolean marketingAgreed;            // (선택) 마케팅 수신 동의
}