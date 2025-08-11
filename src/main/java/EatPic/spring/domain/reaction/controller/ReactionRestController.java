package EatPic.spring.domain.reaction.controller;

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
@RequestMapping("/api/reactions")
@Tag(name = "Reaction", description = "반응 관련 API")
public class ReactionRestController {

    private final ReactionService reactionService;

    @Operation(
            summary = "카드 반응 작성/수정/삭제",
            description = "카드에 반응을 작성합니다. 기존에 작성한 반응이 존재하고 같은 반응을 요청하면 해당 반응이 삭제됩니다")
    @PostMapping("/{cardId}/{reactionType}")
    public ApiResponse<ReactionResponseDTO.ReactionHandleResponseDto> handleReaction(@PathVariable("cardId") Long cardId,
                                                                                     @PathVariable("reactionType") ReactionType reactionType) {
        return ApiResponse.onSuccess(reactionService.handleReaction(cardId,reactionType));
    }

    @Operation(
            summary = "해당 카드에 작성된 반응 별 유저정보",
            description = "reactionType에 따라 반응을 작성한 유저 리스트를 반환합니다")
    @GetMapping("/{cardId}/users")
    public ApiResponse<ReactionResponseDTO.CardReactionUserListDto> CardReactionUsersList(@PathVariable("cardId") Long cardId,
                                                                                          @RequestParam("reactionType") ReactionType reactionType,
                                                                                          @RequestParam(defaultValue = "1") int page,
                                                                                          @RequestParam(defaultValue = "15") int size) {

        return ApiResponse.onSuccess(reactionService.getCardUsersByReactionType(cardId, reactionType, page-1, size));
    }

}
