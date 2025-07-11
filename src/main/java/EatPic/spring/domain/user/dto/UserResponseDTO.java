package EatPic.spring.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserIconListResponseDto{
        private int page;
        private int size;
        private int total;
        private List<ProfileIconDto> userIconList;
    }
}
