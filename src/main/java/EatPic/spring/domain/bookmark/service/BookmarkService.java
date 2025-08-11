package EatPic.spring.domain.bookmark.service;

import EatPic.spring.domain.bookmark.dto.BookmarkResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface BookmarkService {
    BookmarkResponseDTO.BookmarkResponseDto saveBookmark(HttpServletRequest request, Long cardId);
    BookmarkResponseDTO.BookmarkResponseDto deleteBookmark(HttpServletRequest request,Long cardId);
}
