package EatPic.spring.domain.card.controller;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.repository.CardRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
@Tag(name = "Card", description = "카드 관련 API")
public class CardController {

  private final CardRepository cardRepository;

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

}
