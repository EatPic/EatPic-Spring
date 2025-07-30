package EatPic.spring.domain.card.service;

import EatPic.spring.domain.card.dto.request.CardCreateRequest;
import EatPic.spring.domain.card.dto.request.CardCreateRequest.CardUpdateRequest;
import EatPic.spring.domain.card.dto.response.CardResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CardDetailResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CardFeedResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.TodayCardResponse;
import EatPic.spring.domain.user.entity.User;
import java.util.List;

public interface CardService {
  CardResponse.CreateCardResponse createNewCard(CardCreateRequest.CreateCardRequest request, Long userId);
  CardDetailResponse getCardDetail(Long cardId, Long userId);
  CardFeedResponse getCardFeed(Long cardId, Long userId);
  CardResponse.profileCardListDTO getProfileCardList(Long userId, int size, Long cursor);
  void deleteCard(Long cardId, Long userId);
  List<TodayCardResponse> getTodayCards(Long userId);
  CardDetailResponse updateCard(Long cardId, Long userId, CardUpdateRequest request);
}
