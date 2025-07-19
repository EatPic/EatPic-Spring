package EatPic.spring.domain.card.controller;

import EatPic.spring.domain.card.dto.request.CardCreateRequest;
import EatPic.spring.domain.card.dto.response.CardResponse;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.card.service.CardServiceImpl;
import EatPic.spring.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
@Tag(name = "Card", description = "카드 관련 API")
public class CardController {

  private final CardRepository cardRepository;
  private final CardServiceImpl cardService;

  @Operation(summary = "해당 카드 메모 보기", description = "특정 카드의 메모 내용을 반환합니다.")
  @GetMapping("/{cardId}/memo")
  public ResponseEntity<String> getMemo(@PathVariable Long cardId) {
    return cardRepository.findById(cardId)
        .map(Card::getMemo)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @Operation(summary = "해당 카드 레시피 보기", description = "특정 카드의 레시피 내용을 반환합니다.")
  @GetMapping("/{cardId}/recipe")
  public ResponseEntity<String> getRecipe(@PathVariable Long cardId) {
    return cardRepository.findById(cardId)
        .map(Card::getRecipe)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @Operation(summary = "픽카드 생성하기 (픽카드 기록 작성)", description = "픽카드를 생성할 때 호출되는 api")
  @PostMapping("")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "COMMON201", description = "픽카드가 기록되었습니다.")
  })
  public BaseResponse<CardResponse.CreateCardResponse> createCard(
          @Valid @RequestBody CardCreateRequest.CreateCardRequest request
          // 유저 관련 처리는 추후에..
  ) {
    Long userId = 1L;     // 아직 유저 관련 처리를 안해서 처리 미완 상태로 userId는 1로 고정해두었습니다~
    CardResponse.CreateCardResponse result = cardService.createNewCard(request, userId);
    return BaseResponse.onSuccess(result);
  }

}
