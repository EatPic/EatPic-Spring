package EatPic.spring.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileIconDto{
        private Long userId;
        private String profileImageUrl;
        private String nameId;
    }
}
