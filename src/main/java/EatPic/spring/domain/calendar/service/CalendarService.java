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
    List<Card> cards = cardRepository.findByUserIdAndCreatedAtBetween(userId, start, end);

    // 날짜별로 그룹화 (날짜 기준, 카드 리스트)
    Map<LocalDate, List<Card>> grouped = cards.stream()
        .collect(Collectors.groupingBy(card -> card.getCreatedAt().toLocalDate()));

    // 날짜순 정렬
    List<CalendarDayResponse> result = new ArrayList<>();

    grouped.forEach((date, cardList) -> {
      // 아침 → 점심 → 저녁 → 간식 순으로 첫 식사 선택
      Optional<Card> firstMealCard = getFirstMealCard(cardList);

      // 대표 이미지 가져오기
      String imageUrl = firstMealCard
              .map(Card::getCardImageUrl)
              .orElse(null);

      result.add(CalendarDayResponse.builder()
          .date(date)
          .imageUrl(imageUrl)
          .build());
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
    for (Meal mealType : priority) {
      for (Card card : cards) {
        if (card.getMeal() == mealType) {
          return Optional.of(card);
        }
      }
    }
    return Optional.empty();
  }

}
