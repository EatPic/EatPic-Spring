package EatPic.spring.domain.card.controller;

import EatPic.spring.domain.card.dto.response.SearchResponseDTO;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.card.service.SearchServiceImpl;
import EatPic.spring.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
@Tag(name = "Search", description = "탐색 관련 API")
public class SearchController {

    private final SearchServiceImpl searchService;
    private final CardRepository cardRepository;

    @Operation(summary = "탐색 탭에서 모든 유저 리스트 조회",
            description = "기본 탐색 탭 조회 시 호출되는 api")
    @GetMapping("")
    public ApiResponse<SearchResponseDTO.GetCardListResponseDto> getAllUsersInSearch(
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "cursor", required = false) Long cursor
            // 유저 관련 처리는 추후에..
    ) {
        SearchResponseDTO.GetCardListResponseDto result = searchService.getAllCards(limit, cursor);
        return ApiResponse.onSuccess(result);    // 리턴 부분 제대로 작동하는지 확인하기!
    }

    @Operation(summary = "검색 범위가 전체인 경우에서 계정 검색", description = "전체 - 계정 검색 api (nameId로 검색합니다.)")
    @GetMapping("/all/account")
    public ApiResponse<SearchResponseDTO.GetAccountListResponseDto> searchAccountInAll(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "cursor", required = false) Long cursor
            // 유저 관련 처리는 추후에..
    ) {
        SearchResponseDTO.GetAccountListResponseDto result = searchService.getAccountInAll(query, limit, cursor);
        return ApiResponse.onSuccess(result);    // 리턴 부분 제대로 작동하는지 확인하기!
    }

    @Operation(summary = "검색범위가 유저가 팔로우한 사용자인 경우에서 계정 검색", description = "팔로우 - 계정 검색 api")
    @GetMapping("/user-follow/account")
    public ApiResponse<SearchResponseDTO.GetAccountListResponseDto> searchAccountInFollow(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "cursor", required = false) Long cursor
    ) {
        Long userId = 1L;
        SearchResponseDTO.GetAccountListResponseDto result = searchService.getAccountInFollow(query, limit, cursor, userId);
        return ApiResponse.onSuccess(result);    // 리턴 부분 제대로 작동하는지 확인하기!
    }

    @Operation(summary = "검색 범위가 전체인 경우에서 해시태그 검색", description = "전체 - 해시태그 검색 api")
    @GetMapping("/all/hashtag-search")
    public ApiResponse<SearchResponseDTO.GetHashtagListResponseDto> searchHashtagInAll(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "cursor", required = false) Long cursor
    ) {
        Long userId = 1L;
        SearchResponseDTO.GetHashtagListResponseDto result = searchService.getHashtagInAll(query, limit, cursor);
        return ApiResponse.onSuccess(result);    // 리턴 부분 제대로 작동하는지 확인하기!
    }
//
    @Operation(summary = "검색범위가 유저가 팔로우한 사용자인 경우에서 해시태그 검색", description = "팔로우 - 해시태그 검색 api")
    @GetMapping("/user-follow/hashtag")
    public ApiResponse<SearchResponseDTO.GetHashtagListResponseDto> searchHashtagInFollow(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "cursor", required = false) Long cursor
    ) {
        Long userId = 1L;
        SearchResponseDTO.GetHashtagListResponseDto result = searchService.getHashtagInFollow(query, limit, cursor, userId);
        return ApiResponse.onSuccess(result);    // 리턴 부분 제대로 작동하는지 확인하기!
    }
//
//    @Operation(summary = "해시태그 선택 시 해당 해시태그가 포함된 픽카드 리스트 조회", description = "팔로우 - 해시태그 검색 api")
//    @GetMapping("")
//    public ResponseEntity<String> getCardsByHashtag() {
//
//    }
}
