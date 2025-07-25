package EatPic.spring.domain.card.service;

import EatPic.spring.domain.card.dto.request.CardCreateRequest;
import EatPic.spring.domain.card.dto.response.CardResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CardDetailResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CardFeedResponse;

public interface CardService {
  CardResponse.CreateCardResponse createNewCard(CardCreateRequest.CreateCardRequest request, Long userId);
  CardDetailResponse getCardDetail(Long cardId, Long userId);
  CardFeedResponse getCardFeed(Long cardId, Long userId);
}
