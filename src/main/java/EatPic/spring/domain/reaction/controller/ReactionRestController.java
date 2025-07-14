package EatPic.spring.domain.reaction.controller;

import EatPic.spring.domain.comment.converter.CommentConverter;
import EatPic.spring.domain.comment.dto.CommentRequestDTO;
import EatPic.spring.domain.comment.dto.CommentResponseDTO;
import EatPic.spring.domain.comment.entity.Comment;
import EatPic.spring.domain.reaction.converter.ReactionConverter;
import EatPic.spring.domain.reaction.dto.ReactionResponseDTO;
import EatPic.spring.domain.reaction.entity.Reaction;
import EatPic.spring.domain.reaction.entity.ReactionType;
import EatPic.spring.domain.reaction.service.ReactionService;
import EatPic.spring.domain.reaction.service.ReactionServiceImpl;
import EatPic.spring.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class ReactionRestController {

    private final ReactionServiceImpl reactionService;

    @Operation(
            summary = "카드 반응 작성/삭제",
            description = "카드에 반응을 작성합니다. 기존에 작성한 반응이 존재하고 같은 반응을 요청하면 해당 반응이 삭제됩니다")
    @PostMapping("/cards/{cardId}/reactions")
    public BaseResponse<ReactionResponseDTO.ReactionHandleResponseDto> handleReaction(@PathVariable("cardId") Long cardId,
                                               @RequestBody ReactionType reactionType) {

        Reaction reaction = reactionService.handleReaction(cardId, reactionType);

        ReactionResponseDTO.ReactionHandleResponseDto result = ReactionConverter.reactionToReactionHandleResponseDTO(reaction);

        return BaseResponse.onSuccess(result);
    }

}
