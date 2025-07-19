package EatPic.spring.domain.card.converter;

import EatPic.spring.domain.card.dto.request.CardCreateRequest;
import EatPic.spring.domain.card.dto.response.CardResponse;
import EatPic.spring.domain.card.dto.response.SearchResponseDTO;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.user.entity.User;

public class CardConverter {
    // 탐색하기에서 모든 픽카드 조회하기 할 때
    public static SearchResponseDTO.GetCardResponseDto toGetCardResponseDto(
            Card card, Long commentCount, Long reactionCount) {
        return SearchResponseDTO.GetCardResponseDto.builder()
                .id(card.getId())
                .cardImageUrl(card.getCardImageUrl())
                .commentCount(commentCount)
                .reactionCount(reactionCount)
                .build();
    }

    // 요청 DTO → Card 엔티티
    public static Card toCardEntity(CardCreateRequest.CreateCardRequest request, User user) {
        return Card.builder()
                .isShared(request.getIsShared())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .cardImageUrl(request.getCardImageUrl())
                .recipeUrl(request.getRecipeUrl())
                .memo(request.getMemo())
                .recipe(request.getRecipe())
                .meal(request.getMeal())
                .user(user)
                .build();
    }

    // Card 엔티티 → 응답 DTO
    public static CardResponse.CreateCardResponse toCreateCardResponse(Card card) {
        return CardResponse.CreateCardResponse.builder()
                .newcardId(card.getId())
                .isShared(card.getIsShared())
                .latitude(card.getLatitude())
                .longitude(card.getLongitude())
                .cardImageUrl(card.getCardImageUrl())
                .recipeUrl(card.getRecipeUrl())
                .memo(card.getMemo())
                .recipe(card.getRecipe())
                .meal(card.getMeal())
                .build();
    }
}
