package EatPic.spring.domain.bookmark.service;

import EatPic.spring.domain.bookmark.dto.BookmarkResponseDTO;

public interface BookmarkService {
    BookmarkResponseDTO.BookmarkResponseDto saveBookmark(Long cardId);
    BookmarkResponseDTO.BookmarkResponseDto deleteBookmark(Long cardId);
}
