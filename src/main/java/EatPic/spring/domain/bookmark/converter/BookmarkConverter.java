package EatPic.spring.domain.bookmark.converter;

import EatPic.spring.domain.bookmark.dto.BookmarkResponseDTO;
import EatPic.spring.domain.bookmark.entity.Bookmark;

public class BookmarkConverter {
    public static BookmarkResponseDTO.BookmarkResponseDto bookmarkToBookmarkResponseDTO(Bookmark bookmark,String status){
        return BookmarkResponseDTO.BookmarkResponseDto.builder()
                .cardId(bookmark.getCard().getId())
                .userId(bookmark.getUser().getId())
                .status(status)
                .build();
    }
}
