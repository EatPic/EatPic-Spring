package EatPic.spring.domain.greetingMessage.service;

import EatPic.spring.domain.greetingMessage.dto.GreetingResponse;
import EatPic.spring.domain.greetingMessage.entity.GreetingMessage;
import EatPic.spring.domain.greetingMessage.entity.TimeType;
import EatPic.spring.domain.greetingMessage.repository.GreetingMessageRepository;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.repository.UserRepository;
import java.time.LocalTime;

import EatPic.spring.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GreetingService {

  private final GreetingMessageRepository greetingMessageRepository;
  private final UserRepository userRepository;
  private final UserService userService;

  @Transactional
  public GreetingResponse getGreeting(HttpServletRequest request) {
    // 사용자 조회
    User user = userService.getLoginUser(request);

    // 현재 시간대 계산
    TimeType timeType = getCurrentTimeType();

    // 해당 시간대 인사말 조회
    GreetingMessage greetingMessage = greetingMessageRepository.findByTimeType(timeType)
        .orElseThrow(() -> new RuntimeException("Greeting message not found"));

    return new GreetingResponse(user.getNickname(), greetingMessage.getMessage());
  }


  private TimeType getCurrentTimeType() {
    int hour = LocalTime.now().getHour();
    if (hour < 12) {
      return TimeType.MORNING;
    } else if (hour < 18) {
      return TimeType.AFTERNOON;
    } else {
      return TimeType.EVENING;
    }
  }
}
