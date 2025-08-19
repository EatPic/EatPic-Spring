package EatPic.spring.domain.card.service;

import EatPic.spring.domain.card.dto.response.CardResponse;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.entity.Meal;
import EatPic.spring.domain.card.mapping.CardHashtag;
import EatPic.spring.domain.card.repository.CardHashtagRepository;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.card.repository.HashtagRepository;
import EatPic.spring.domain.comment.repository.CommentRepository;
import EatPic.spring.domain.hashtag.entity.Hashtag;
import EatPic.spring.domain.reaction.repository.ReactionRepository;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.entity.UserStatus;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
public class CardServiceImplTest {

    @Autowired
    private CardServiceImpl cardService; // 테스트할 서비스 객체

    @MockBean // 실제 UserService 대신 가짜 UserService를 주입합니다.
    private UserService userService;

    // 서비스가 의존하는 Repository들을 주입
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ReactionRepository reactionRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private HashtagRepository hashtagRepository;

    @Autowired
    private CardHashtagRepository cardHashtagRepository;

    private User me;
    private MockHttpServletRequest request;

    // 테스트 클래스 내부 헬퍼 메소드,
    private User createTestUser(String email, String nameId, String nickname) {
        return User.builder()
                .email(email)
                .password("password123")
                .nameId(nameId)
                .nickname(nickname)
                .userStatus(UserStatus.ACTIVE)
                .marketingAgreed(true)
                .notificationAgreed(true)
                .build();
    }
    private Card createTestCard(User user, List<CardHashtag> hashtagList) {
        return Card.builder()
                .user(user)
                .cardImageUrl("https://example.com/image.jpg")
                .meal(Meal.DINNER)
                .isShared(true)
                .cardHashtags(new ArrayList<>(hashtagList)) // 가변 리스트 OK!
                .build();
    }
    private Hashtag createTestHashtag(User user, String name) {
        return Hashtag.builder()
                .user(user)
                .hashtagName(name)
                .build();
    }
    private CardHashtag createCardHashtagMapping(Card card, Hashtag hashtag) {
        return CardHashtag.builder()
                .card(card)
                .hashtag(hashtag)
                .build();
    }
    @BeforeEach
    void setUp() {
        User user = createTestUser("testCode@example.com", "testuser123", "테스트유저");
        me = userRepository.save(user);
        request = new MockHttpServletRequest();
        given(userService.getLoginUser(any(HttpServletRequest.class))).willReturn(me);
    }

    @Test
    void getCardFeedByCursor() {
        // given
        // 테스트 카드 및 해시태그, CardHashtag 매핑 준비
        Hashtag hashtag1 = createTestHashtag(me, "여행");
        Hashtag hashtag2 = createTestHashtag(me, "맛집");
        hashtag1 = hashtagRepository.save(hashtag1);
        hashtag2 = hashtagRepository.save(hashtag2);

        final Card card1 = createTestCard(me, new ArrayList<>());
        cardRepository.save(card1);
        final Card card2 = createTestCard(me, new ArrayList<>());
        cardRepository.save(card2);

        // 해시태그-카드 매핑
        CardHashtag ch1 = createCardHashtagMapping(card1, hashtag1);
        CardHashtag ch2 = createCardHashtagMapping(card2, hashtag2);
        cardHashtagRepository.save(ch1);
        cardHashtagRepository.save(ch2);
        for(CardHashtag h : card1.getCardHashtags()){
            System.out.println(h.getCard().getId() + " " + h.getHashtag().getId() );
        }
        // 필드 초기화 등 (필요하면 card1, card2에 해시태그 세팅)
        card1.getCardHashtags().add(ch1);
        card2.getCardHashtags().add(ch2);

        // when
        int pageSize = 2;
        Long cursor = null; // 첫 페이지
        var response = cardService.getCardFeedByCursor(request, me.getId(), pageSize, cursor);

        // then
        assertNotNull(response.getCardFeedList());
        // 페이지 사이즈 만큼 나오는지 확인 (생성한 card 수와 비교)
        assertTrue(response.getCardFeedList().size() <= pageSize);

        // 실제 카드들이 반환되었는지 확인
        boolean hasCardId1 = response.getCardFeedList().stream().anyMatch(dto -> dto.getCardId().equals(card1.getId()));
        boolean hasCardId2 = response.getCardFeedList().stream().anyMatch(dto -> dto.getCardId().equals(card2.getId()));
        assertTrue(hasCardId1 || hasCardId2);
    }
}