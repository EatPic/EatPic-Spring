package EatPic.spring.domain.user.service;

import EatPic.spring.domain.badge.entity.Badge;
import EatPic.spring.domain.badge.entity.ConditionType;
import EatPic.spring.domain.badge.repository.BadgeRepository;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserBadgeService {
  private final UserBadgeRepository userBadgeRepository;
  private final UserRepository userRepository;
  private final BadgeRepository badgeRepository;

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

  // 처음 회원가입 시 UserBadge 생성하는 로직
  @Transactional
  public void initializeUserBadges(User user) {
    List<Badge> allBadges = badgeRepository.findAll();
    for (Badge badge : allBadges) {
      UserBadge userBadge = UserBadge.builder()
          .user(user)
          .badge(badge)
          .currentValue(0)
          .progressRate(0)
          .isAchieved(false)
          .build();
      userBadgeRepository.save(userBadge);
    }
  }

  @Transactional
  public void checkAndAssignBadges(User user, ConditionType conditionType, int increment) {
    // 조건에 해당하는 모든 뱃지 가져오기 (여러 개일 수 있음)
    List<Badge> badges = badgeRepository.findAll().stream()
        .filter(b -> b.getConditionType() == conditionType)
        .toList();

    for (Badge badge : badges) {
      // 해당 유저의 해당 뱃지 가져오기
      UserBadge userBadge = userBadgeRepository.findByUser(user).stream()
          .filter(ub -> ub.getBadge().getId().equals(badge.getId()))
          .findFirst()
          .orElse(null);

      if (userBadge == null || userBadge.isAchieved()) continue;

      // currentValue 증가
      int updatedValue = userBadge.getCurrentValue() + increment;
      userBadge.updateCurrentValue(updatedValue);

      // 진도율 갱신
      int progressRate = Math.min((int) (((double) updatedValue / badge.getConditionValue()) * 100), 100);
      userBadge.updateProgressRate(progressRate);

      // 조건 달성 여부 확인
      if (updatedValue >= badge.getConditionValue()) {
        userBadge.updateAchieved(true);
      }

      // 저장 (dirty checking 또는 repository.save)
    }
  }  //badgeConditionService.checkAndAssignBadges(user, ConditionType.CARD_UPLOAD, 1); 이런식으로 사용하기


}
