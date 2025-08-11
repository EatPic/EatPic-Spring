package EatPic.spring.domain.user.service;

import EatPic.spring.domain.badge.entity.Badge;
import EatPic.spring.domain.badge.entity.ConditionType;
import EatPic.spring.domain.badge.repository.BadgeRepository;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.reaction.entity.ReactionType;
import EatPic.spring.domain.reaction.repository.ReactionRepository;
import EatPic.spring.domain.user.converter.UserBadgeConverter;
import EatPic.spring.domain.user.dto.response.UserBadgeResponse.BadgeDetailResponseDTO;
import EatPic.spring.domain.user.dto.response.UserBadgeResponse.HomeBadgeResponse;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.mapping.UserBadge;
import EatPic.spring.domain.user.repository.UserBadgeRepository;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.global.common.code.status.ErrorStatus;
import EatPic.spring.global.common.exception.handler.ExceptionHandler;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserBadgeService {
  private final UserBadgeRepository userBadgeRepository;
  private final UserRepository userRepository;
  private final BadgeRepository badgeRepository;
  private final CardRepository cardRepository;
  private final ReactionRepository reactionRepository;
  private final UserService userService;

  // 홈화면 뱃지 리스트 조회
  public List<HomeBadgeResponse> getUserBadgesForHome(HttpServletRequest request, Long userId) {
    User user = userService.getLoginUser(request); // 로그인 적용할 때 바꾸기!! 인자 User로 받기!
    List<UserBadge> userBadges = userBadgeRepository.findByUser(user);
    return UserBadgeConverter.toHomeBadgeDTOList(userBadges);
  }

  public BadgeDetailResponseDTO getBadgeDetail(HttpServletRequest request,Long userId, Long userBadgeId) {
    User user = userService.getLoginUser(request);
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

//      // currentValue 증가
//      int updatedValue = userBadge.getCurrentValue() + increment;
//      userBadge.updateCurrentValue(updatedValue);
//
//      // 진도율 갱신
//      int progressRate = Math.min((int) (((double) updatedValue / badge.getConditionValue()) * 100), 100);
//      userBadge.updateProgressRate(progressRate);
//
//      // 조건 달성 여부 확인
//      if (updatedValue >= badge.getConditionValue()) {
//        userBadge.updateAchieved(true);
//      }

      switch (conditionType) {
        case CONSECUTIVE_DAYS -> handleConsecutiveDays(user, badge, userBadge);
        case WEEKLY_DAYS -> handleWeeklyDays(user, badge, userBadge);
        case CARD_LIKES_RECEIVED -> handleCardLikesReceived(user, badge, userBadge);
        default -> handleSimpleIncrement(badge, userBadge, increment);
      }



      // 저장 (dirty checking 또는 repository.save)
    }
  }  //badgeConditionService.checkAndAssignBadges(user, ConditionType.CARD_UPLOAD, 1); 이런식으로 사용하기

  // 뱃지 처리 기본 로직
  private void handleSimpleIncrement(Badge badge, UserBadge userBadge, int increment) {
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
  }

  // "한달러" 뱃지 처리: 최근 N주간 매주 5일 이상 기록했는지 확인
  private void handleWeeklyDays(User user, Badge badge, UserBadge userBadge) {
    int qualifiedWeeks = 0; // 조건을 만족한 주차 수
    LocalDate today = LocalDate.now();

    // 최근 N주 동안 반복 검사 (N = badge.getConditionValue())
    for (int week = 0; week < badge.getConditionValue(); week++) {
      int daysRecorded = 0;  // 해당 주에 기록한 일 수

      // 한 주는 월~일, 총 7일 반복
      for (int day = 0; day < 7; day++) {
        // 기준일: 이번 주에서 week만큼 지난 주의 월요일 + day일
        LocalDate dateToCheck = today.minusWeeks(week).with(DayOfWeek.MONDAY).plusDays(day);
        // 해당 날짜에 카드 기록이 있는지 확인
        boolean exists = cardRepository.existsByUserAndCreatedAtBetween(
            user, dateToCheck.atStartOfDay(), dateToCheck.atTime(LocalTime.MAX));
        if (exists) daysRecorded++; // 기록이 있으면 일 수 증가
      }

      // 5일 이상 기록 시 해당 주는 조건 충족
      if (daysRecorded >= 5) qualifiedWeeks++;  // 조건 충족한 주차 증가
      else {
        // 한 주라도 조건 미달이면 연속성 깨짐 → 초기화
        qualifiedWeeks = 0;
        break;
      }
    }

    // 조건 충족: N주 이상 연속으로 충족했는가?
    if (qualifiedWeeks >= badge.getConditionValue()) {
      userBadge.updateAchieved(true); // 뱃지 달성
      userBadge.updateProgressRate(100);
      userBadge.updateCurrentValue(qualifiedWeeks);
    } else {
      userBadge.updateAchieved(false); // 아직 조건 미달
      userBadge.updateProgressRate(qualifiedWeeks == 0 ? 0 :
          Math.min((int) (((double) qualifiedWeeks / badge.getConditionValue()) * 100), 100));
      userBadge.updateCurrentValue(qualifiedWeeks);
    }
  }

  // "일주완료" 뱃지 처리: 최근 N일간 연속으로 기록했는지 확인
  private void handleConsecutiveDays(User user, Badge badge, UserBadge userBadge) {
    int consecutiveDays = 0;  // 연속 기록 일 수
    LocalDate currentDate = LocalDate.now(); // 오늘 기준

    for (int i = 0; i < badge.getConditionValue(); i++) {
      LocalDate dateToCheck = currentDate.minusDays(i); // 오늘 - i일

      // 해당 날짜에 카드 기록이 있는지 확인
      boolean exists = cardRepository.existsByUserAndCreatedAtBetween(
          user, dateToCheck.atStartOfDay(), dateToCheck.atTime(LocalTime.MAX));
      if (exists) consecutiveDays++; // 연속 기록 +1
      else break; // 하루라도 빠지면 연속 종료
    }

    // 조건 달성 여부 확인
    if (consecutiveDays >= badge.getConditionValue()) {
      userBadge.updateAchieved(true);  // 뱃지 획득
      userBadge.updateProgressRate(100);
      userBadge.updateCurrentValue(consecutiveDays);
    } else {
      userBadge.updateAchieved(false); // 미달성
      userBadge.updateProgressRate(consecutiveDays == 0 ? 0 :
          Math.min((int) (((double) consecutiveDays / badge.getConditionValue()) * 100), 100));
      userBadge.updateCurrentValue(consecutiveDays);
    }
  }

  // 사진 장인 뱃지 처리
  private void handleCardLikesReceived(User user, Badge badge, UserBadge userBadge) {
    // 유저가 작성한 카드 중, 좋아요가 20개 이상인 카드 개수 조회
    List<Card> userCards = cardRepository.findByUser(user);
    long likedCardsCount = userCards.stream()
        .filter(card -> reactionRepository.countByCardAndReactionType(card, ReactionType.THUMB_UP) >= 20)
        .count();

    int currentValue = (int) likedCardsCount;

    // 진도율 계산
    int progressRate = Math.min((int) (((double) currentValue / badge.getConditionValue()) * 100), 100);

    // 갱신
    userBadge.updateCurrentValue(currentValue);
    userBadge.updateProgressRate(progressRate);
    userBadge.updateAchieved(currentValue >= badge.getConditionValue());
  }

}
