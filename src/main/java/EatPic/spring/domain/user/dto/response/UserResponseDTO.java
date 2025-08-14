package EatPic.spring.domain.user.dto.response;

import jakarta.validation.constraints.NotNull;
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
        @NotNull
        private Long userId;
        @NotNull
        private String profileImageUrl;
        @NotNull
        private String nameId;
        @NotNull
        private String nickname;
        @NotNull
        private Boolean isFollowing;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserIconListResponseDto{
        @NotNull
        private int page;
        @NotNull
        private int size;
        @NotNull
        private int total;
        private List<ProfileDto> userIconList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserActionResponseDto {
        @NotNull
        Long userId;
        @NotNull
        Long targetUserId;
    }



}
