package EatPic.spring.domain.user.dto.response;

import EatPic.spring.domain.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SignupResponseDTO {
    private Role role;                   // 역할
    private Long userId;                 // DB에 저장된 유저 ID
    private String email;                // 회원가입 시 입력한 이메일
    private String nameId;               // 사용자 ID
    private String nickname;             // 닉네임
    private Boolean marketingAgreed;     // 마케팅 수신 동의 여부
    private Boolean notificationAgreed;  // 알림 수신 동의 여부
    private String message;              // 예: "회원가입이 완료되었습니다."}
}