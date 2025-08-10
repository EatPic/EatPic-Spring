package EatPic.spring.domain.user.dto.response;

import EatPic.spring.domain.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private Role role;
    private Long userId;            // DB에 저장된 유저 ID
    private String email;           // 회원가입 시 입력한 이메일
    private String accessToken;
    private String refreshToken;
}
