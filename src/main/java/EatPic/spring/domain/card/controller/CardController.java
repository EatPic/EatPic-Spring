package EatPic.spring.domain.card.controller;

import EatPic.spring.domain.card.dto.request.CardCreateRequest;
import EatPic.spring.domain.card.dto.request.CardCreateRequest.CardUpdateRequest;
import EatPic.spring.domain.card.dto.response.CardResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CardDetailResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CardFeedResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CreateCardResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.TodayCardResponse;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.card.service.CardService;
import EatPic.spring.domain.comment.dto.CommentResponseDTO;
import EatPic.spring.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
@Tag(name = "Card", description = "카드 관련 API")
public class CardController {

  private final CardRepository cardRepository;
  private final CardService cardService;

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

  //픽카드 생성하기 부분에서 같은 날짜에, 같은 mealtype으로 픽카드 등록되지 않도록 수정해야함
  @PostMapping("")
  @Operation(summary = "픽카드 생성하기 (픽카드 기록 작성)", description = "픽카드를 생성할 때 호출되는 api")
  public ApiResponse<CreateCardResponse> createCard(
      @Valid @RequestBody CardCreateRequest.CreateCardRequest request) {
    Long userId = 1L;

    return ApiResponse.onSuccess(cardService.createNewCard(request, userId));
  }

  @GetMapping("/{cardId}")
  @Operation(summary = "카드 상세 조회 (홈화면에서)", description = "카드 ID를 기준으로 상세 정보를 조회하는 API")
  public ApiResponse<CardDetailResponse> getCardDetail(@PathVariable Long cardId) {
    Long userId = 1L; // 로그인 기능 구현 전 임시 사용자
    return ApiResponse.onSuccess(cardService.getCardDetail(cardId, userId));
  }

  @GetMapping("/{cardId}/feed")
  @Operation(summary = "카드 1개만 피드 조회 ", description = "카드 1개만 피드로 조회할 때 사용되는 상세 정보를 반환합니다.")
  public ApiResponse<CardFeedResponse> getCardFeed(@PathVariable Long cardId) {
    Long userId = 1L; // 추후 인증에서 가져올 예정
    return ApiResponse.onSuccess(cardService.getCardFeed(cardId, userId));
  }

  @DeleteMapping("/{cardId}")
  @Operation(summary = "카드 삭제", description = "카드를 소프트 삭제 처리합니다.")
  public ApiResponse<Void> deleteCard(@PathVariable Long cardId) {
    Long userId = 1L; // 로그인 인증 전 임시
    cardService.deleteCard(cardId, userId);
    return ApiResponse.onSuccess(null);
  }

  @Operation(summary = "오늘의 식사 카드 조회", description = "홈 진입 시, 오늘 등록된 식사 카드들을 조회합니다.")
  @GetMapping("/home/today-cards")
  public ApiResponse<List<TodayCardResponse>> getTodayCards(
      //@AuthUser User user
  ) {
    Long userId = 1L;
    return ApiResponse.onSuccess(cardService.getTodayCards(userId));
  }

  @Operation(summary = "픽카드 수정", description = "카드의 메모, 레시피, 위치 정보 등을 수정합니다.")
  @PutMapping("/api/cards/{cardId}")
  public ResponseEntity<ApiResponse<CardDetailResponse>> updateCard(
      @Parameter(description = "수정할 카드 ID", example = "12")
      @PathVariable Long cardId,
      @RequestBody CardUpdateRequest request)
  {
    Long userId = 1L;
    return ResponseEntity.ok(ApiResponse.onSuccess(cardService.updateCard(cardId, userId, request)));
  }

  @Operation(
          summary = "피드 조회",
          description = "특정 사용자(null이면 전체 사용자)의 최근 7일 동안의 피드를 조회합니다.(전체, 본인의 경우 전체 피드를 조회합니다)")
  @GetMapping("/community/feeds")
  public ApiResponse<CardResponse.PagedCardFeedResponseDto> getFeeds(@RequestParam(required = false) Long userId,
                                                                     @RequestParam(required = false) Long cursor,
                                                                     @RequestParam(defaultValue = "15") int size) {
    return ApiResponse.onSuccess(cardService.getCardFeedByCursor(userId,size,cursor));
  }


}
