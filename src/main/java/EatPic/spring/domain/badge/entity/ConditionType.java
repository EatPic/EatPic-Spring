package EatPic.spring.domain.badge.entity;

public enum ConditionType {
    CARD_UPLOAD,            // 식사 카드 업로드 수 1회 -한끼했당 50회 -피드요정
    FULL_DAY_MEALS,         // 하루 아점저 전부 기록 -삼시세끼
    HASHTAG_USAGE_ALONE,          // 특정 해시태그 (혼밥 해시태그) 사용 횟수 10회 -혼밥러
    LOCATION_INCLUDED,      // 위치 포함 외식 사진 횟수 10회 -맛집왕
    REACTION_GIVEN,         // 남의 피드에 반응한 횟수 30회 -공감짱
    RECIPE_SHARED,          // 레시피 링크 포함 카드 수 5회 -공유잼
    CONSECUTIVE_DAYS,       // 연속 기록 일수 7회 -일주완료
    WEEKLY_DAYS,            // 주간 기록 일수 (4주간 5일 이상) -한달러
    CARD_LIKES_RECEIVED     // 좋아요 20개 이상 받은 카드 수 5개 -사진장인
}
