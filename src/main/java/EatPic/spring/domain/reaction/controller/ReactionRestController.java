package EatPic.spring.domain.reaction.controller;

import EatPic.spring.domain.reaction.converter.ReactionConverter;
import EatPic.spring.domain.reaction.dto.ReactionRequestDTO;
import EatPic.spring.domain.reaction.dto.ReactionResponseDTO;
import EatPic.spring.domain.reaction.entity.Reaction;
import EatPic.spring.domain.reaction.entity.ReactionType;
import EatPic.spring.domain.reaction.service.ReactionService;
import EatPic.spring.domain.reaction.service.ReactionServiceImpl;
import EatPic.spring.domain.user.converter.UserConverter;
import EatPic.spring.domain.user.dto.UserResponseDTO;
import EatPic.spring.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class ReactionRestController {

    private final ReactionServiceImpl reactionService;

    @Operation(
            summary = "카드 반응 작성/수정/삭제",
            description = "카드에 반응을 작성합니다. 기존에 작성한 반응이 존재하고 같은 반응을 요청하면 해당 반응이 삭제됩니다"+
    "<br> ReactionType : \"THUMB_UP\", \"HEART\", \"YUMMY\", \"POWER\", \"LAUGH\"")
    @PostMapping("/cards/{cardId}/reactions")
    public BaseResponse<ReactionResponseDTO.ReactionHandleResponseDto> handleReaction(@PathVariable("cardId") Long cardId,
                                               @RequestBody ReactionRequestDTO.ReactionHandleRequestDto reactionHandleRequestDto) {
        Reaction reaction = reactionService.handleReaction(cardId, reactionHandleRequestDto.getReactionType());

        return BaseResponse.onSuccess(ReactionConverter.reactionToReactionHandleResponseDTO(reaction));
    }

    @Operation(
            summary = "해당 카드에 작성된 반응 별 유저정보",
            description = "reactionType에 따라 반응을 작성한 유저 리스트를 반환합니다")
    @GetMapping("/cards/{cardId}/reactions-users")
    public BaseResponse<ReactionResponseDTO.CardReactionUserListDto> CardReactionUsersList(@PathVariable("cardId") Long cardId,
                                                                                  @RequestParam("reactionType") ReactionType reactionType,
                                                                                  @RequestParam(defaultValue = "1") int page,
                                                                                  @RequestParam(defaultValue = "15") int size) {

        return BaseResponse.onSuccess(reactionService.getCardUsersByReactionType(cardId, reactionType, page-1, size));
    }

}
