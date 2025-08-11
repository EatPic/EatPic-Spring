package EatPic.spring.domain.card.service;

import EatPic.spring.domain.card.dto.response.SearchResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface SearchService {
    SearchResponseDTO.GetCardListResponseDto getAllCards(HttpServletRequest request, int limit, Long cursor);
    SearchResponseDTO.GetAccountListResponseDto getAccountInAll(HttpServletRequest request, String query, int limit, Long cursor);
    SearchResponseDTO.GetAccountListResponseDto getAccountInFollow(HttpServletRequest request, String query, int limit, Long cursor);
    SearchResponseDTO.GetHashtagListResponseDto getHashtagInFollow(HttpServletRequest request, String query, int limit, Long cursor);
    SearchResponseDTO.GetHashtagListResponseDto getHashtagInAll(HttpServletRequest request, String query, int limit, Long cursor);
    SearchResponseDTO.GetCardListResponseDto getCardsByHashtag(HttpServletRequest request, Long hashtagId, int limit, Long cursor);
}
