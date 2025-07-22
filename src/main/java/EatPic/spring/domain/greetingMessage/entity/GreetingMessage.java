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

    //데베에 이거 명령어 입력해서 넣어줘야함
//    INSERT INTO greeting_messages (time_type, message, created_at, updated_at)
//    VALUES
//        ('MORNING', '안녕하세요, 좋은 아침이에요!', NOW(), NOW()),
//        ('AFTERNOON', '좋은 점심시간입니다!', NOW(), NOW()),
//        ('EVENING', '편안한 저녁 되세요!', NOW(), NOW());
}

