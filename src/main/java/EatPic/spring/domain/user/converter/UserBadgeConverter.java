package EatPic.spring.domain.user.converter;

import EatPic.spring.domain.user.dto.response.UserBadgeResponse.HomeBadgeResponse;
import EatPic.spring.domain.user.mapping.UserBadge;
import java.util.List;
import java.util.stream.Collectors;

public class UserBadgeConverter {

  public static List<HomeBadgeResponse> toHomeBadgeDTOList(List<UserBadge> userBadges) {
    return userBadges.stream()
        .map(ub -> HomeBadgeResponse.builder()
            .userBadgeId(ub.getUserBadgeId())
            .badgeName(ub.getBadge().getName())
            .badgeImageUrl(ub.getBadge().getBadgeImageUrl())
            .progressRate(ub.getProgressRate())
            .isAchieved(ub.isAchieved())
            .build())
        .sorted((a, b) -> b.getProgressRate() - a.getProgressRate()) // 이행률 내림차순
        .collect(Collectors.toList());
  }

}
