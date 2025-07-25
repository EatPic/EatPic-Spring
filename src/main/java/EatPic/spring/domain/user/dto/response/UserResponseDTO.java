package EatPic.spring.domain.user.dto.response;

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
    public static class ProfileDto {
        private Long userId;
        private String profileImageUrl;
        private String nameId;
        private String nickname;
        private Boolean isFollowing;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserIconListResponseDto{
        private int page;
        private int size;
        private int total;
        private List<ProfileDto> userIconList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserBlockResponseDto{
        Long userId;
        Long targetUserId;
    }

}
