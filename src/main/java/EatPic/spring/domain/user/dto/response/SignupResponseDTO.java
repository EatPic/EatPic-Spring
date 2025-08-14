package EatPic.spring.domain.user.dto.response;

import EatPic.spring.domain.user.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SignupResponseDTO {
    @NotNull
    private Role role;                   // 역할
    @NotNull
    private Long userId;                 // DB에 저장된 유저 ID
    @NotNull
    private String email;                // 회원가입 시 입력한 이메일
    @NotNull
    private String nameId;               // 사용자 ID
    @NotNull
    private String nickname;             // 닉네임
    @NotNull
    private Boolean marketingAgreed;     // 마케팅 수신 동의 여부
    @NotNull
    private Boolean notificationAgreed;  // 알림 수신 동의 여부
}

