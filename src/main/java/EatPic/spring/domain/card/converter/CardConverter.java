package EatPic.spring.domain.card.converter;

import EatPic.spring.domain.card.dto.response.SearchResponseDTO;
import EatPic.spring.domain.card.entity.Card;

public class CardConverter {
    public static SearchResponseDTO.GetCardResponseDto toGetCardResponseDto(
            Card card, Long commentCount, Long reactionCount) {
        return SearchResponseDTO.GetCardResponseDto.builder()
                .id(card.getId())
                .cardImageUrl(card.getCardImageUrl())
                .commentCount(commentCount)
                .reactionCount(reactionCount)
                .build();
    }

}
