package EatPic.spring.domain.user.controller;

import EatPic.spring.domain.user.dto.response.UserBadgeResponse.BadgeDetailResponseDTO;
import EatPic.spring.domain.user.dto.response.UserBadgeResponse.HomeBadgeResponse;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.repository.UserBadgeRepository;
import EatPic.spring.domain.user.service.UserBadgeService;
import EatPic.spring.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/badges")
@Tag(name = "UserBadge", description = "사용자 배찌 관련 API")
public class UserBadgeController {

  private final UserBadgeRepository userBadgeRepository;
  private final UserBadgeService userBadgeService;

  @GetMapping("/home")
  @Operation(summary = "홈화면 진입 시 유저 뱃지 리스트 이행률순 조회 ", description = "홈화면에서 유저가 보유한 뱃지 리스트를 이행률 기준으로 정렬하여 조회합니다.")
  public ApiResponse<List<HomeBadgeResponse>> getUserBadgesForHome() {
    Long userId = 1L;
    List<HomeBadgeResponse> responses = userBadgeService.getUserBadgesForHome(userId);
    return ApiResponse.onSuccess(responses);
  }

  @Operation(summary = "유저 뱃지 상세 조회", description = "뱃지 팝업에 들어갈 상세 정보를 조회합니다.")
  @GetMapping("/my/{userBadgeId}")
  public ApiResponse<BadgeDetailResponseDTO> getBadgeDetail(
      @PathVariable Long userBadgeId) {
    Long userId = 1L;
    return ApiResponse.onSuccess(userBadgeService.getBadgeDetail(userId, userBadgeId));
  }

}
