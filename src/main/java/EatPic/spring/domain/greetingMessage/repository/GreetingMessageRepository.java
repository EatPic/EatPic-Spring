package EatPic.spring.domain.greetingMessage.repository;

import EatPic.spring.domain.greetingMessage.entity.GreetingMessage;
import EatPic.spring.domain.greetingMessage.entity.TimeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreetingMessageRepository extends JpaRepository<GreetingMessage, Long> {
  Optional<GreetingMessage> findByTimeType(TimeType timeType);

}
