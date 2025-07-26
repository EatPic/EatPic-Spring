package EatPic.spring.domain.bookmark.service;

import EatPic.spring.domain.bookmark.controller.BookmarkController;
import EatPic.spring.domain.bookmark.converter.BookmarkConverter;
import EatPic.spring.domain.bookmark.dto.BookmarkResponseDTO;
import EatPic.spring.domain.bookmark.entity.Bookmark;
import EatPic.spring.domain.bookmark.entity.BookmarkId;
import EatPic.spring.domain.bookmark.repository.BookmarkRepository;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.user.converter.UserConverter;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.global.common.code.status.ErrorStatus;
import EatPic.spring.global.common.exception.GeneralException;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static EatPic.spring.global.common.code.status.ErrorStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkServiceImpl implements BookmarkService{
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;


    @Override
    public BookmarkResponseDTO.BookmarkResponseDto saveBookmark(Long cardId) {
        User user = userRepository.findUserById(1L);
        Card card = cardRepository.findById(cardId).orElseThrow(()-> new GeneralException(CARD_NOT_FOUND));

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .card(card)
                .build();
        if(bookmarkRepository.existsById(new BookmarkId(1L,cardId))){
            throw new GeneralException(ALREADY_BOOKMARKED);
        }
        bookmarkRepository.save(bookmark);

        return BookmarkConverter.bookmarkToBookmarkResponseDTO(bookmark,"SAVED");
    }

    @Override
    public BookmarkResponseDTO.BookmarkResponseDto deleteBookmark(Long cardId) {
        User user = userRepository.findUserById(1L);
        Card card = cardRepository.findById(cardId).orElseThrow(()-> new GeneralException(CARD_NOT_FOUND));

        Bookmark bookmark = bookmarkRepository.findById(new BookmarkId(1L,cardId))
                .orElseThrow(() -> new GeneralException(BOOKMARK_NOT_FOUND));

        bookmarkRepository.delete(bookmark);

        return BookmarkConverter.bookmarkToBookmarkResponseDTO(bookmark,"DELETED");

    }
}
