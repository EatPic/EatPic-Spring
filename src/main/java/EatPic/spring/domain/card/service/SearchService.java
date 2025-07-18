package EatPic.spring.domain.card.service;

import EatPic.spring.domain.card.dto.response.CardResponse;
import EatPic.spring.domain.card.entity.Card;

import java.util.List;

public abstract class SearchService {
    // public abstract List<Card> getAllCards(int limit, Long cursor);
    public abstract CardResponse.GetCardListResponseDto getAllCards(int limit, Long cursor);
}
