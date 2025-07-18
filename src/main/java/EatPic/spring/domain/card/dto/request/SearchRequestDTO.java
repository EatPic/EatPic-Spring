package EatPic.spring.domain.card.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SearchRequestDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetCardRequestDto {
        @Schema(description = "가져올 픽카드 개수")
        private int limit;

        @Schema(description = "커서")
        private Long cursor;
    }
}
