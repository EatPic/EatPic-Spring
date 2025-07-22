package EatPic.spring.domain.card.service;

import EatPic.spring.domain.card.dto.request.CardCreateRequest;
import EatPic.spring.domain.card.dto.response.CardResponse;

public interface CardService {
  CardResponse.CreateCardResponse createNewCard(CardCreateRequest.CreateCardRequest request, Long userId);
}
