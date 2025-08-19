package EatPic.spring.domain.card.converter;

import EatPic.spring.domain.card.dto.request.CardCreateRequest;
import EatPic.spring.domain.card.dto.response.CardResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CardDeleteResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CardDetailResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CardFeedResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CardFeedUserDTO;
import EatPic.spring.domain.card.dto.response.CardResponse.NextMealCard;
import EatPic.spring.domain.card.dto.response.CardResponse.TodayCardResponse;
import EatPic.spring.domain.card.dto.response.SearchResponseDTO;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.mapping.CardHashtag;
import EatPic.spring.domain.hashtag.entity.Hashtag;
import EatPic.spring.domain.reaction.entity.Reaction;
import EatPic.spring.domain.user.entity.User;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public class CardConverter {
    // 탐색하기에서 모든 픽카드 조회하기 할 때
    public static SearchResponseDTO.GetCardResponseDto toGetCardResponseDto(
            Card card, int commentCount, int reactionCount) {
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
                .datetime(card.getCreatedAt())
                .mealType(card.getMeal())
                .recipeUrl(card.getRecipeUrl())
                .latitude(card.getLatitude())
                .longitude(card.getLongitude())
                .locationText(card.getLocationText())
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
                .datetime(card.getCreatedAt())
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
                        .nameId(writer.getNameId())
                        .profileImageUrl(writer.getProfileImageUrl())
                        .build())
                .reactionCount(totalReactionCount)
                .userReaction(userReaction != null ? userReaction.getReactionType().name() : null)
                .commentCount(commentCount)
                .isBookmarked(isBookmarked)
                .build();
    }

    public static TodayCardResponse toTodayCard(Card card) {
        return TodayCardResponse.builder()
                .cardId(card.getId())
                .cardImageUrl(card.getCardImageUrl())
                .meal(card.getMeal())
                .build();
    }

    public static CardResponse.PagedCardFeedResponseDto toPagedCardFeedResponseDTto(Long userId, Slice<Card> cardSlice, List<CardFeedResponse> feedList) {
        return CardResponse.PagedCardFeedResponseDto.builder()
                .selectedId(userId)
                .hasNext(cardSlice.hasNext())
                .nextCursor(cardSlice.hasNext() ? cardSlice.getContent().get(cardSlice.getContent().size() - 1).getId() : null)
                .cardFeedList(feedList)
                .build();
    }

    // 해시태그로 검색된 픽카드 리스트 조회
    public static SearchResponseDTO.GetCardResponseDto toCardResponseDto(
            Card card,
            int commentCount,
            int reactionCount
    ) {
        return SearchResponseDTO.GetCardResponseDto.builder()
                .id(card.getId())
                .cardImageUrl(card.getCardImageUrl())
                .commentCount(commentCount)
                .reactionCount(reactionCount)
                .build();
    }

    public static SearchResponseDTO.GetHashtagResponseDto toHashtagDto(Hashtag hashtag, long cardCount) {
        return SearchResponseDTO.GetHashtagResponseDto.builder()
                .hashtagId(hashtag.getId())
                .hashtagName(hashtag.getHashtagName())
                .card_count(cardCount)
                .build();
    }

    public static CardDeleteResponse toCardDeleteResponse(Card card) {
        return CardDeleteResponse.builder()
            .cardId(card.getId())
            .successMessage("카드 삭제 성공")
            .build();
    }

    public static CardResponse.ProfileCardDTO toProfileCardDto(Card card){
        return CardResponse.ProfileCardDTO.builder()
                .cardId(card.getId())
                .cardImageUrl(card.getCardImageUrl())
                .build();
    }

    public static CardResponse.ProfileCardListDTO toProfileCardList(Long userId, Slice<Card> cardList) {
        return CardResponse.ProfileCardListDTO.builder()
                .hasNext(cardList.hasNext())
                .nextCursor(cardList.hasNext() ? cardList.getContent().get(cardList.getContent().size() - 1).getId() : null)
                .userId(userId)
                .cardsList(cardList.getContent().stream()
                        .map(CardConverter::toProfileCardDto)
                        .toList())
                .build();
    }
}
