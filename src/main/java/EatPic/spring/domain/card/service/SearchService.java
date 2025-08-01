package EatPic.spring.domain.card.service;

import EatPic.spring.domain.card.dto.response.SearchResponseDTO;

public interface SearchService {
    SearchResponseDTO.GetCardListResponseDto getAllCards(int limit, Long cursor);
    SearchResponseDTO.GetAccountListResponseDto getAccountInAll(String query, int limit, Long cursor);
}
