package EatPic.spring.domain.card.service;

import EatPic.spring.domain.card.dto.response.SearchResponseDTO;

public interface SearchService {
    public abstract SearchResponseDTO.GetCardListResponseDto getAllCards(int limit, Long cursor);
}
