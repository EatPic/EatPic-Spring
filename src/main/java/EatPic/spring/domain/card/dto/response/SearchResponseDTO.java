package EatPic.spring.domain.card.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class SearchResponseDTO {
    // 탐색하기 탭에서 전체 픽카드 조회할 때~ (픽카드 하나 ver)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetCardResponseDto {
        @JsonProperty("card_id")
        @NotNull
        private Long id;

        @JsonProperty("card_image_url")
        @NotNull
        private String cardImageUrl;
        @NotNull
        private int commentCount;
        @NotNull
        private int reactionCount;
    }

    // 탐색하기 탭에서 전체 픽카드 조회할 때~ (카드 배열이랑 페이지 정보까지)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetCardListResponseDto {
        @NotNull
        private List<GetCardResponseDto> cards;
        private Long nextCursor;
        @NotNull
        private int size;
        @NotNull
        private boolean hasNext;
    }

    // 탐색하기 검색창에서 검색 범위가 전체일 때 계정 검색하기 (계정 하나 ver)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAccountResponseDto {
        @JsonProperty("user_id")
        @NotNull
        private Long userId;

        @JsonProperty("name_id")
        @NotNull
        private String nameId;             // 유저 아이디

        @JsonProperty("nickname")
        @NotNull
        private String nickname;           // 유저 닉네임

        @JsonProperty("profile_image_url")
        @NotNull
        private String profileImageUrl;     // 프사
    }

    // 탐색하기 검색창에서 검색 범위가 전체일 때 계정 검색하기 (계정 여러 개 리스트로..)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAccountListResponseDto {
        private List<GetAccountResponseDto> accounts;
        private Long nextCursor;
        @NotNull
        private int size;
        @NotNull
        private boolean hasNext;
    }

    // 탐색하기 검색창에서 검색 범위가 전체일 때 해시태그 검색하기 (해시태그 하나 ver)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetHashtagResponseDto {
        @JsonProperty("hashtag_id")
        @NotNull
        private Long hashtagId;

        @JsonProperty("hashtag_name")
        @NotNull
        private String hashtagName;

        @JsonProperty("card_count")
        @NotNull
        private Long card_count;
    }

    // 탐색하기 검색창에서 검색 범위가 전체일 때 해시태그 검색하기 (해시태그 여러 개 리스트로..)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetHashtagListResponseDto {
        @NotNull
        private List<GetHashtagResponseDto> hashtags;
        private Long nextCursor;
        @NotNull
        private int size;
        @NotNull
        private boolean hasNext;
    }
}
