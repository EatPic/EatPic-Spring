package EatPic.spring.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private Long userId;            // DB에 저장된 유저 ID
    private String email;           // 회원가입 시 입력한 이메일
    private String nickname;        // 닉네임
    private String accessToken;
    private String refreshToken;
}
