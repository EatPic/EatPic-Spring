package EatPic.spring.domain.greetingMessage.entity;

import EatPic.spring.domain.card.entity.TimeType;
import EatPic.spring.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "greeting_messages")
public class GreetingMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "greeting_message_id", nullable = false)
    private Long id;

    // 아침/점심/저녁 마다 그리팅 메시지 변경
    @Enumerated(EnumType.STRING)
    @Column(name = "time_type", nullable = false)
    private TimeType timeType;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;
}
