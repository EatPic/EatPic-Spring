package EatPic.spring.domain.calendar.service;

import EatPic.spring.domain.calendar.dto.CalendarDayResponse;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.entity.Meal;
import EatPic.spring.domain.card.repository.CardRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {
  private final CardRepository cardRepository;

  public List<CalendarDayResponse> getCalendar(Long userId, int year, int month) {
    // 현재 월과 이전 월 구하기
    YearMonth currentMonth = YearMonth.of(year, month);
    YearMonth previousMonth = currentMonth.minusMonths(1);

    // 시작 날짜: 이전달 1일 00:00 / 끝 날짜: 이번달 말일 23:59:59
    LocalDateTime start = previousMonth.atDay(1).atStartOfDay();
    LocalDateTime end = currentMonth.atEndOfMonth().atTime(LocalTime.MAX);

    // 해당 기간 동안의 카드들 조회
    List<Card> cards = cardRepository.findByUserIdAndCreatedAtBetween(userId, start, end)
        .stream()
        .filter(card -> !card.isDeleted()) // 삭제된 카드 제외
        .toList();

    // 카드들을 생성일(LocalDate) 기준으로 그룹화 (날짜별로 카드 묶기)
    Map<LocalDate, List<Card>> grouped = cards.stream()
        .collect(Collectors.groupingBy(card -> card.getCreatedAt().toLocalDate()));

    // 결과 리스트 초기화
    List<CalendarDayResponse> result = new ArrayList<>();


    //각 날짜별로 대표 카드 선정 (식사 우선순위 기준: 아침 > 점심 > 저녁 > 간식)
    grouped.forEach((date, cardList) -> {
      Optional<Card> firstMealCard = getFirstMealCard(cardList);
      if (firstMealCard.isPresent()) {
        result.add(CalendarDayResponse.builder()
            .date(date) // 해당 날짜
            .imageUrl(firstMealCard.get().getCardImageUrl()) // 대표 카드 이미지
            .cardId(firstMealCard.get().getId()) // 대표 카드 ID
            .build());
      }
    });

    // 최신 날짜가 아래로 오도록 정렬
    result.sort(Comparator.comparing(CalendarDayResponse::getDate).reversed());

    return result;
  }

  // 식사 우선순위 정해서 대표 카드 선택
  private Optional<Card> getFirstMealCard(List<Card> cards) {
    List<Meal> priority = List.of(
        Meal.BREAKFAST,
        Meal.LUNCH,
        Meal.DINNER,
        Meal.SNACK
    );
    // 식사 우선순위에 따라 하나씩 탐색
    for (Meal mealType : priority) {
      for (Card card : cards) {
        if (card.getMeal() == mealType) {
          return Optional.of(card); // 가장 우선순위 높은 카드 반환
        }
      }
    }
    return Optional.empty();
  }

}
