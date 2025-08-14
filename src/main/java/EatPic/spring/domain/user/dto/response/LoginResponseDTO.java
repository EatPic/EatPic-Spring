package EatPic.spring.domain.user.dto.response;

import EatPic.spring.domain.user.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    @NotNull
    private Role role;
    @NotNull
    private Long userId;
    // DB에 저장된 유저 ID
    @NotNull
    private String email;           // 회원가입 시 입력한 이메일
    @NotNull
    private String accessToken;
    @NotNull
    private String refreshToken;
}
