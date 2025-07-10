package EatPic.spring.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SignupResponse {
    private Long userId;            // DB에 저장된 유저 ID
    private String email;           // 회원가입 시 입력한 이메일
    private String nameId;          // 사용자 ID
    private String nickname;        // 닉네임
    private Boolean marketingAgree; // 마케팅 수신 동의 여부
    private String message;         // 예: "회원가입이 완료되었습니다."
}
