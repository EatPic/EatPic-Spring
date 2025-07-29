package EatPic.spring.domain.bookmark.controller;

import EatPic.spring.domain.bookmark.dto.BookmarkResponseDTO;
import EatPic.spring.domain.bookmark.service.BookmarkService;
import EatPic.spring.domain.reaction.converter.ReactionConverter;
import EatPic.spring.domain.reaction.dto.ReactionResponseDTO;
import EatPic.spring.domain.reaction.entity.Reaction;
import EatPic.spring.domain.reaction.entity.ReactionType;
import EatPic.spring.domain.reaction.service.ReactionService;
import EatPic.spring.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
@Tag(name = "Bookmark", description = "반응 관련 API")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(
            summary = "카드 저장",
            description = "해당 카드를 저장합니다.")
    @PostMapping("/cards/{cardId}/bookmark")
    public ApiResponse<BookmarkResponseDTO.BookmarkResponseDto> saveBookmark(@PathVariable("cardId") Long cardId) {
        return ApiResponse.onSuccess(bookmarkService.saveBookmark(cardId));
    }

    @Operation(
            summary = "카드 저장 취소",
            description = "해당 카드의 저장을 취소합니다.")
    @DeleteMapping("/cards/{cardId}/bookmark")
    public ApiResponse<BookmarkResponseDTO.BookmarkResponseDto> deleteBookmark(@PathVariable("cardId") Long cardId) {
        return ApiResponse.onSuccess(bookmarkService.deleteBookmark(cardId));
    }

}
