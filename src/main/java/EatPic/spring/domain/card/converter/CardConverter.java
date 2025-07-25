package EatPic.spring.domain.card.converter;

import EatPic.spring.domain.card.dto.request.CardCreateRequest;
import EatPic.spring.domain.card.dto.response.CardResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CardDetailResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CardFeedResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CardFeedUserDTO;
import EatPic.spring.domain.card.dto.response.CardResponse.NextMealCard;
import EatPic.spring.domain.card.dto.response.SearchResponseDTO;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.mapping.CardHashtag;
import EatPic.spring.domain.reaction.entity.Reaction;
import EatPic.spring.domain.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;

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

    public static CardDetailResponse toCardDetailResponse(Card card, Long nextCardId) {
        return CardDetailResponse.builder()
            .cardId(card.getId())
            .imageUrl(card.getCardImageUrl())
            .date(card.getCreatedAt().toLocalDate())
            .time(card.getCreatedAt().toLocalTime())
            .mealType(card.getMeal())
            .recipeUrl(card.getRecipeUrl())
            .latitude(card.getLatitude())
            .longitude(card.getLongitude())
            .memo(card.getMemo())
            .recipe(card.getRecipe())
            .nextMeal(nextCardId != null ?
                NextMealCard.builder().cardId(nextCardId).build() : null)
            .build();
    }

    public static CardResponse.CardFeedResponse toFeedResponse(
        Card card,
        List<CardHashtag> cardHashtags,
        User writer,
        Reaction userReaction,
        int totalReactionCount,
        int commentCount,
        boolean isBookmarked) {
        return CardResponse.CardFeedResponse.builder()
            .cardId(card.getId())
            .imageUrl(card.getCardImageUrl())
            .date(card.getCreatedAt().toLocalDate())
            .time(card.getCreatedAt().toLocalTime())
            .meal(card.getMeal())
            .memo(card.getMemo())
            .recipe(card.getRecipe())
            .recipeUrl(card.getRecipeUrl())
            .latitude(card.getLatitude())
            .longitude(card.getLongitude())
            .locationText(card.getLocationText())
            .hashtags(cardHashtags.stream()
                .map(ch -> ch.getHashtag().getHashtagName())
                .collect(Collectors.toList()))
            .user(CardResponse.CardFeedUserDTO.builder()
                .userId(writer.getId())
                .nickname(writer.getNickname())
                .profileImageUrl(writer.getProfileImageUrl())
                .build())
            .reactionCount(totalReactionCount)
            .userReaction(userReaction != null ? userReaction.getReactionType().name() : null)
            .commentCount(commentCount)
            .isBookmarked(isBookmarked)
            .build();
    }
}
