package EatPic.spring.domain.card.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
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
        private Long id;

        @JsonProperty("card_image_url")
        private String cardImageUrl;

        private int commentCount;
        private int reactionCount;
    }

    // 탐색하기 탭에서 전체 픽카드 조회할 때~ (카드 배열이랑 페이지 정보까지)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetCardListResponseDto {

        private List<GetCardResponseDto> cards;
        private Long nextCursor;
        private int size;
        private boolean hasNext;
    }

    // 탐색하기 검색창에서 검색 범위가 전체일 때 계정 검색하기 (계정 하나 ver)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAccountResponseDto {
        @JsonProperty("user_id")
        private Long userId;

        @JsonProperty("name_id")
        private String nameId;             // 유저 아이디

        @JsonProperty("nickname")
        private String nickname;           // 유저 닉네임

        @JsonProperty("profile_image_url")
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
        private int size;
        private boolean hasNext;
    }

    // 탐색하기 검색창에서 검색 범위가 전체일 때 해시태그 검색하기 (해시태그 하나 ver)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetHashtagResponseDto {
        @JsonProperty("hashtag_id")
        private Long hashtagId;

        @JsonProperty("hashtag_name")
        private String hashtagName;

        @JsonProperty("card_count")
        private Long card_count;
    }

    // 탐색하기 검색창에서 검색 범위가 전체일 때 해시태그 검색하기 (해시태그 여러 개 리스트로..)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetHashtagListResponseDto {
        private List<GetHashtagResponseDto> hashtags;
        private Long nextCursor;
        private int size;
        private boolean hasNext;
    }
}
