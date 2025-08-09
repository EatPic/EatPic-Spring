package EatPic.spring.domain.card.service;

import EatPic.spring.domain.card.dto.response.SearchResponseDTO;

public interface SearchService {
    SearchResponseDTO.GetCardListResponseDto getAllCards(int limit, Long cursor);
    SearchResponseDTO.GetAccountListResponseDto getAccountInFollow(Long userId, String query, int limit, Long cursor);
    SearchResponseDTO.GetAccountListResponseDto getAccountInAll(String query, int limit, Long cursor);
    SearchResponseDTO.GetAccountListResponseDto getAccountInFollow(String query, int limit, Long cursor, Long userId);
    // SearchResponseDTO.GetCardListResponseDto getCardsByHashtag(Long hashtagId, int limit, Long cursor);
}
