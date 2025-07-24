package EatPic.spring.domain.user.service;

import EatPic.spring.domain.user.converter.UserBadgeConverter;
import EatPic.spring.domain.user.dto.response.UserBadgeResponse.BadgeDetailResponseDTO;
import EatPic.spring.domain.user.dto.response.UserBadgeResponse.HomeBadgeResponse;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.mapping.UserBadge;
import EatPic.spring.domain.user.repository.UserBadgeRepository;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.global.common.code.status.ErrorStatus;
import EatPic.spring.global.common.exception.handler.ExceptionHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBadgeService {
  private final UserBadgeRepository userBadgeRepository;
  private final UserRepository userRepository;

  // 홈화면 뱃지 리스트 조회
  public List<HomeBadgeResponse> getUserBadgesForHome(Long userId) {
    User user = userRepository.findUserById(userId); // 로그인 적용할 때 바꾸기!! 인자 User로 받기!
    List<UserBadge> userBadges = userBadgeRepository.findByUser(user);
    return UserBadgeConverter.toHomeBadgeDTOList(userBadges);
  }

  public BadgeDetailResponseDTO getBadgeDetail(Long userId, Long userBadgeId) {
    User user = userRepository.findUserById(userId);
    UserBadge userBadge = userBadgeRepository.findByUser_IdAndUserBadgeId(user.getId(), userBadgeId)
        .orElseThrow(() -> new ExceptionHandler(ErrorStatus._BAD_REQUEST));
    return UserBadgeConverter.toBadgeDetailResponse(userBadge);
  }

}
