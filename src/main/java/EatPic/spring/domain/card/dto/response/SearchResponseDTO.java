package EatPic.spring.domain.card.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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

        private Long commentCount;
        private Long reactionCount;
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
}
