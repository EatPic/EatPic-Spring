package EatPic.spring.domain.badge.service;


import EatPic.spring.domain.badge.entity.Badge;
import EatPic.spring.domain.badge.entity.ConditionType;
import EatPic.spring.domain.badge.repository.BadgeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BadgeInitializer {
  private final BadgeRepository badgeRepository;

  @PostConstruct
  public void initBadges() {
    if (!badgeRepository.findAll().isEmpty()) return; // 이미 등록된 경우 skip

    badgeRepository.save(Badge.builder()
        .name("한끼했당")
        .description("식사 사진을 1회 업로드하면 받을 수 있습니다.")
        .badgeImageUrl("https://cdn.eatpic.com/badges/badge1.png")
        .conditionType(ConditionType.CARD_UPLOAD)
        .conditionValue(1)
        .build());

    badgeRepository.save(Badge.builder()
        .name("삼시세끼")
        .description("하루 아침·점심·저녁 사진을 모두 등록하면 받을 수 있습니다.")
        .badgeImageUrl("https://cdn.eatpic.com/badges/badge2.png")
        .conditionType(ConditionType.FULL_DAY_MEALS)
        .conditionValue(1) // 하루 단위이므로 1
        .build());

    badgeRepository.save(Badge.builder()
        .name("혼밥러")
        .description("‘혼밥’ 해시태그를 10회 이상 사용하면 받을 수 있습니다.")
        .badgeImageUrl("https://cdn.eatpic.com/badges/badge3.png")
        .conditionType(ConditionType.HASHTAG_USAGE_ALONE)
        .conditionValue(10)
        .build());

    badgeRepository.save(Badge.builder()
        .name("맛집왕")
        .description("위치 포함 외식 사진을 10회 이상 올리면 받을 수 있습니다.")
        .badgeImageUrl("https://cdn.eatpic.com/badges/badge4.png")
        .conditionType(ConditionType.LOCATION_INCLUDED)
        .conditionValue(10)
        .build());

    badgeRepository.save(Badge.builder()
        .name("공감짱")
        .description("다른 유저 피드에 반응을 30회 이상 하면 받을 수 있습니다.")
        .badgeImageUrl("https://cdn.eatpic.com/badges/badge5.png")
        .conditionType(ConditionType.REACTION_GIVEN)
        .conditionValue(30)
        .build());

    badgeRepository.save(Badge.builder()
        .name("공유잼")
        .description("레시피 링크가 포함된 카드를 5개 이상 작성하면 받을 수 있습니다.")
        .badgeImageUrl("https://cdn.eatpic.com/badges/badge6.png")
        .conditionType(ConditionType.RECIPE_SHARED)
        .conditionValue(5)
        .build());

    badgeRepository.save(Badge.builder()
        .name("피드요정")
        .description("식사 카드를 50개 이상 업로드하면 받을 수 있습니다.")
        .badgeImageUrl("https://cdn.eatpic.com/badges/badge7.png")
        .conditionType(ConditionType.CARD_UPLOAD)
        .conditionValue(50)
        .build());

    badgeRepository.save(Badge.builder()
        .name("일주완료")
        .description("7일 연속 기록하면 받을 수 있습니다.")
        .badgeImageUrl("https://cdn.eatpic.com/badges/badge8.png")
        .conditionType(ConditionType.CONSECUTIVE_DAYS)
        .conditionValue(7)
        .build());

    badgeRepository.save(Badge.builder()
        .name("한달러")
        .description("4주간 매주 5일 이상 기록하면 받을 수 있습니다.")
        .badgeImageUrl("https://cdn.eatpic.com/badges/badge9.png")
        .conditionType(ConditionType.WEEKLY_DAYS)
        .conditionValue(4) // 주간 단위로 4주
        .build());

    badgeRepository.save(Badge.builder()
        .name("사진장인")
        .description("좋아요 20개 이상 받은 피드를 5개 이상 보유하면 받을 수 있습니다.")
        .badgeImageUrl("https://cdn.eatpic.com/badges/badge10.png")
        .conditionType(ConditionType.CARD_LIKES_RECEIVED)
        .conditionValue(5)
        .build());
  }

}