package EatPic.spring.domain.card.service;

import EatPic.spring.domain.card.dto.response.SearchResponseDTO;

public interface SearchService {
    SearchResponseDTO.GetCardListResponseDto getAllCards(int limit, Long cursor);
    SearchResponseDTO.GetAccountListResponseDto getAccountInAll(String query, int limit, Long cursor);
    SearchResponseDTO.GetAccountListResponseDto getAccountInFollow(String query, int limit, Long cursor, Long userId);
    SearchResponseDTO.GetHashtagListResponseDto getHashtagInFollow(String query, int limit, Long cursor, Long userId);
    SearchResponseDTO.GetHashtagListResponseDto getHashtagInAll(String query, int limit, Long cursor);
    SearchResponseDTO.GetCardListResponseDto getCardsByHashtag(String hashtag, int limit, Long cursor);
}
