package EatPic.spring.domain.card.controller;

import EatPic.spring.domain.card.dto.request.CardCreateRequest;
import EatPic.spring.domain.card.dto.request.CardCreateRequest.CardUpdateRequest;
import EatPic.spring.domain.card.dto.response.CardResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CardDeleteResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CardDetailResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CardFeedResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CreateCardResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.RecommendCardResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.TodayCardResponse;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.card.service.CardService;
import EatPic.spring.domain.comment.dto.CommentResponseDTO;
import EatPic.spring.domain.user.dto.response.UserResponseDTO;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.service.UserService;
import EatPic.spring.global.common.ApiResponse;
import EatPic.spring.global.common.code.status.ErrorStatus;
import EatPic.spring.global.common.exception.GeneralException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
@Tag(name = "Card", description = "카드 관련 API")
public class CardController {
  private final CardService cardService;
  private final UserService userService;

  //픽카드 생성하기 부분에서 같은 날짜에, 같은 mealtype으로 픽카드 등록되지 않도록 수정해야함
  @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "픽카드 생성하기 (픽카드 기록 + 이미지 작성)", description = "기록 부분 string으로 받아서 JSON 파싱을 합니다.")
  public ApiResponse<CardResponse.CreateCardResponse> createCard(
        HttpServletRequest req,
        @RequestPart(value = "cardImageFile", required = false) MultipartFile cardImageFile,
        @Valid @RequestPart(value = "request") CardCreateRequest.CreateCardRequest request
  ) {
  User user = userService.getLoginUser(req);

    CardResponse.CreateCardResponse response = cardService.createNewCard(req, request, user, cardImageFile);
    return ApiResponse.onSuccess(response);
  }

  @GetMapping("/{cardId}")
  @Operation(summary = "카드 상세 조회 (홈화면에서)", description = "카드 ID를 기준으로 상세 정보를 조회하는 API")
  public ApiResponse<CardDetailResponse> getCardDetail(
          HttpServletRequest request,
          @PathVariable(name = "cardId") Long cardId
  ) {

    User user = userService.getLoginUser(request);
    return ApiResponse.onSuccess(cardService.getCardDetail(cardId, user.getId()));
  }

  @GetMapping("/{cardId}/feed")
  @Operation(
          summary = "카드 1개만 피드 조회 ",
          description = "카드 1개만 피드로 조회할 때 사용되는 상세 정보를 반환합니다."
  )
  public ApiResponse<CardFeedResponse> getCardFeed(
          HttpServletRequest request,
          @Parameter(description = "카드 ID", required = true)
          @PathVariable(name = "cardId") Long cardId

  ) {
    User user = userService.getLoginUser(request);
    return ApiResponse.onSuccess(cardService.getCardFeed(cardId, user.getId()));
  }


  @DeleteMapping("/{cardId}")
  @Operation(summary = "카드 삭제", description = "카드를 소프트 삭제 처리합니다.")
  public ApiResponse<CardDeleteResponse> deleteCard(
          HttpServletRequest request,
          @PathVariable(name = "cardId") Long cardId) {
    User user = userService.getLoginUser(request);
    CardDeleteResponse response = cardService.deleteCard(cardId, user.getId());
    return ApiResponse.onSuccess(response);
  }

  @Operation(summary = "오늘의 식사 카드 조회", description = "홈 진입 시, 오늘 등록된 식사 카드들을 조회합니다.")
  @GetMapping("/home/today-cards")
  public ApiResponse<List<TodayCardResponse>> getTodayCards(
          HttpServletRequest request
  ) {
    User user = userService.getLoginUser(request);
    return ApiResponse.onSuccess(cardService.getTodayCards(user.getId()));
  }

  @Operation(summary = "픽카드 수정", description = "카드의 메모, 레시피, 위치 정보 등을 수정합니다.")
  @PutMapping("/{cardId}")
  public ResponseEntity<ApiResponse<CardDetailResponse>> updateCard(
          HttpServletRequest req,
          @Parameter(description = "수정할 카드 ID", example = "12")
          @PathVariable(name = "cardId") Long cardId,
          @RequestBody CardUpdateRequest request)
  {
    User user = userService.getLoginUser(req);
    return ResponseEntity.ok(ApiResponse.onSuccess(cardService.updateCard(cardId, user, request)));
  }

  @Operation(
          summary = "피드 조회",
          description = "특정 사용자(null이면 전체 사용자)의 최근 7일 동안의 피드를 조회합니다.(전체, 본인의 경우 전체 피드를 조회합니다)")
  @GetMapping("/feeds")
  public ApiResponse<CardResponse.PagedCardFeedResponseDto> getFeeds(
          HttpServletRequest request,
          @RequestParam(value = "userId", required = false) Long userId,
          @RequestParam(value = "cursor", required = false) Long cursor,
          @RequestParam(value = "size", defaultValue = "15") int size) {
    return ApiResponse.onSuccess(cardService.getCardFeedByCursor(request,userId,size,cursor));
  }


  @Operation(summary = "추천 픽카드 조회", description = "홈화면 진입 시 추천 픽카드 최대 10개를 조회합니다.")
  @GetMapping("/recommended-cards")
  public ApiResponse<List<RecommendCardResponse>> getRecommendedCards(
          HttpServletRequest request
  ) {
    User user = userService.getLoginUser(request);
    return ApiResponse.onSuccess(cardService.getRecommendedCardPreviews(user.getId()));
  }

  @GetMapping("/profile/{userId}")
  @Operation(summary = "프로필 화면 피드 미리보기", description = "공유한 카드의 번호와 이미지 url 조회 API")
  public ApiResponse<CardResponse.ProfileCardListDTO> getProfileCardsList(@PathVariable Long userId,
                                                                          @RequestParam(required = false) Long cursor,
                                                                          @RequestParam(defaultValue = "15") int size) {
    return ApiResponse.onSuccess(cardService.getProfileCardList(userId, size, cursor));
  }


}
