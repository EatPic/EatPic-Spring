package EatPic.spring.domain.greetingMessage.entity;

import EatPic.spring.global.common.code.BaseEntity;
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

    //데베에 직접 값 넣어줌 에러처리 필요
//    INSERT INTO greeting_messages (time_type, message, created_at, updated_at)
//    VALUES
//        ('MORNING', '오늘도 활기차게 Pic 카드 기록을 시작해볼까요?', NOW(), NOW()),
//        ('AFTERNOON', '점심은 맛있게 드셨나요? Pic 카드를 남겨주세요!', NOW(), NOW()),
//        ('EVENING', '오늘의 식사를 돌아보며 기록해봐요.', NOW(), NOW());
}

