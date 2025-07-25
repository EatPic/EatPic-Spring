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
    public static class ProfileIconDto {
        private Long userId;
        private String profileImageUrl;
        private String nameId;
        private String nickname;
        private Boolean isFollowing;
        private String introduce;
    }

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
    public static class DetailProfileDto {
        private Long userId;
        private String profileImageUrl;
        private String nameId;
        private String nickname;
        private Boolean isFollowing;
        private String introduce;
        private Long totalCard;
        private Long totalFollower;
        private Long totalFollowing;
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

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserBlockResponseDto{
        Long userId;
        Long targetUserId;
    }

}
