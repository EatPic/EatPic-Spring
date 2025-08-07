package EatPic.spring.domain.card.service;

import EatPic.spring.domain.badge.entity.ConditionType;
import EatPic.spring.domain.bookmark.repository.BookmarkRepository;
import EatPic.spring.domain.card.converter.CardConverter;
import EatPic.spring.domain.card.dto.request.CardCreateRequest;
import EatPic.spring.domain.card.dto.request.CardCreateRequest.CardUpdateRequest;
import EatPic.spring.domain.card.dto.response.CardResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CardDetailResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.CardFeedResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.RecommendCardResponse;
import EatPic.spring.domain.card.dto.response.CardResponse.TodayCardResponse;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.entity.Meal;
import EatPic.spring.domain.card.mapping.CardHashtag;
import EatPic.spring.domain.card.repository.CardHashtagRepository;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.comment.repository.CommentRepository;
import EatPic.spring.domain.reaction.entity.Reaction;
import EatPic.spring.domain.reaction.repository.ReactionRepository;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.domain.user.service.UserBadgeService;
import EatPic.spring.global.common.code.status.ErrorStatus;
import EatPic.spring.global.common.exception.handler.ExceptionHandler;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static EatPic.spring.global.common.code.status.ErrorStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final ReactionRepository reactionRepository;
    private final CardHashtagRepository cardHashtagRepository;
    private final CommentRepository commentRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserBadgeService userBadgeService;


    @Override
    @Transactional
    public CardResponse.CreateCardResponse createNewCard(CardCreateRequest.CreateCardRequest request, Long userId) {

        // 아직 유저 관련 처리 안했음
        User user = userRepository.findUserById(userId);

        // 오늘 날짜 00:00부터 23:59:59까지 범위 계산
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        // 같은 날짜, 같은 meal 타입 카드 중복 확인
        boolean existsSameMealCard = cardRepository.existsByUserIdAndMealAndCreatedAtBetween(
                userId,
                request.getMeal(),
                startOfDay,
                endOfDay
        );

        if (existsSameMealCard) {
            throw new ExceptionHandler(ErrorStatus.DUPLICATE_MEAL_CARD);
        }

        Card newcard = Card.builder()
                .isShared(request.getIsShared())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .cardImageUrl(request.getCardImageUrl())
                .recipeUrl(request.getRecipeUrl())
                .memo(request.getMemo())
                .recipe(request.getRecipe())
                .meal(request.getMeal())
                .user(user)
                .build();

        Card savedCard = cardRepository.save(newcard);

        // 뱃지 획득 부분 처리

        // 1. 한끼했당, 7. 피드요정 뱃지 처리
        userBadgeService.checkAndAssignBadges(user, ConditionType.CARD_UPLOAD, 1);

        // 4. 맛집왕 뱃지 처리 (위치 포함 여부 확인)
        if (savedCard.hasLocation()) {
            userBadgeService.checkAndAssignBadges(user, ConditionType.LOCATION_INCLUDED, 1);
        }

        // 3. 혼밥러 뱃지 처리 (혼밥 해시태그 포함 여부 확인)
        if (savedCard.containsHashtag("혼밥")) {
            userBadgeService.checkAndAssignBadges(user, ConditionType.HASHTAG_USAGE_ALONE, 1);
        }

        // 6. 공유잼 뱃지 처리 (레시피 포함 여부 확인)
        if (savedCard.hasRecipeUrl()) {
            userBadgeService.checkAndAssignBadges(user, ConditionType.RECIPE_SHARED, 1);
        }

        // 2. 삼시세끼 뱃지 처리 (카드 저장 이후, 해당 날짜 기준 유저의 카드 3끼 여부 확인)
        if (cardRepository.existsByUserAndCreatedAtBetweenAndMeal(user, startOfDay, endOfDay, Meal.BREAKFAST) &&
            cardRepository.existsByUserAndCreatedAtBetweenAndMeal(user, startOfDay, endOfDay, Meal.LUNCH) &&
            cardRepository.existsByUserAndCreatedAtBetweenAndMeal(user, startOfDay, endOfDay, Meal.DINNER)) {

            userBadgeService.checkAndAssignBadges(user, ConditionType.FULL_DAY_MEALS, 1);
        }

        // 8. 일주완료 뱃지 처리
        userBadgeService.checkAndAssignBadges(user, ConditionType.CONSECUTIVE_DAYS, 1);
        // 9. 한달러 뱃지 처리
        userBadgeService.checkAndAssignBadges(user, ConditionType.WEEKLY_DAYS, 1);



        log.info("새 카드 생성 완료 - ID: {}", savedCard.getId());
        return CardResponse.CreateCardResponse.builder()
                .newcardId(savedCard.getId())
                .isShared(savedCard.getIsShared())
                .latitude(savedCard.getLatitude())
                .longitude(savedCard.getLongitude())
                .cardImageUrl(savedCard.getCardImageUrl())
                .recipeUrl(savedCard.getRecipeUrl())
                .memo(savedCard.getMemo())
                .recipe(savedCard.getRecipe())
                .meal(savedCard.getMeal())
                .build();
    }

    @Override
    @Transactional
    public CardDetailResponse getCardDetail(Long cardId, Long userId) {
        Card selectedCard = cardRepository.findById(cardId)
            .orElseThrow(() -> new ExceptionHandler(ErrorStatus.CARD_NOT_FOUND));

        // 1. 해당 카드의 작성 날짜
        LocalDate date = selectedCard.getCreatedAt().toLocalDate();

        // 2. 같은 유저 + 같은 날짜 카드 목록
        List<Card> cardsOnSameDate = cardRepository.findByUserIdAndCreatedAtBetween(
            userId,
            date.atStartOfDay(),
            date.atTime(LocalTime.MAX)
        );

        // 3. meal 기준 정렬 (ex. 아침 → 점심 → 저녁)
        cardsOnSameDate.sort(Comparator.comparing(Card::getMeal));

        // 4. 다음 카드 찾기
        Long nextCardId = null;
        for (int i = 0; i < cardsOnSameDate.size(); i++) {
            if (cardsOnSameDate.get(i).getId().equals(cardId) && i + 1 < cardsOnSameDate.size()) {
                nextCardId = cardsOnSameDate.get(i + 1).getId();
                break;
            }
        }

        // 5. 응답 DTO 생성
        return CardConverter.toCardDetailResponse(selectedCard, nextCardId);
    }

    @Override
    @Transactional
    public CardFeedResponse getCardFeed(Long cardId, Long userId) {
        Card card = cardRepository.findById(cardId)
            .orElseThrow(() -> new ExceptionHandler(ErrorStatus.CARD_NOT_FOUND));

        List<CardHashtag> hashtags = cardHashtagRepository.findByCard(card); // 해당 카드의 해시태그들 조회
        Reaction reaction = reactionRepository.findByCardIdAndUserId(cardId, userId).orElse(null); // 사용자의 반응 조회
        int reactionCount = reactionRepository.countByCardId(cardId); // 총 반응 수 조회
        int commentCount = commentRepository.countByCardId(cardId); // 댓글 수 조회
        boolean isBookmarked = bookmarkRepository.existsByCardIdAndUserId(cardId, userId); // 북마크 여부
        User writer = card.getUser(); // 작성자 정보 얻기

        return CardConverter.toFeedResponse(
            card, hashtags, writer, reaction, reactionCount, commentCount, isBookmarked
        );
    }

    @Override
    @Transactional
    public void deleteCard(Long cardId, Long userId) {
        Card card = cardRepository.findByIdAndIsDeletedFalse(cardId)
            .orElseThrow(() -> new ExceptionHandler(ErrorStatus.CARD_NOT_FOUND));

        if (!card.getUser().getId().equals(userId)) {
            throw new ExceptionHandler(ErrorStatus.CARD_DELETE_FORBIDDEN);
        }

        card.softDelete(); // isDeleted = true, deletedAt = now()
    }

    @Override
    @Transactional
    public List<TodayCardResponse> getTodayCards(Long userId) {
        User user = userRepository.findUserById(userId);
        LocalDate today = LocalDate.now();
        List<Card> todayCards = cardRepository.findAllByUserAndCreatedAtBetween(
            user,
            today.atStartOfDay(),
            today.plusDays(1).atStartOfDay()
        );

        return todayCards.stream()
            .map(CardConverter::toTodayCard)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CardDetailResponse updateCard(Long cardId, Long userId, CardUpdateRequest request) {
        Card card = cardRepository.findById(cardId)
            .orElseThrow(() -> new ExceptionHandler(ErrorStatus.CARD_NOT_FOUND));

        if (!card.getUser().getId().equals(userId)) {
            throw new ExceptionHandler(ErrorStatus.CARD_UPDATE_FORBIDDEN);
        }

        card.update(
            request.getMemo(),
            request.getRecipe(),
            request.getRecipeUrl(),
            request.getLatitude(),
            request.getLongitude(),
            request.getLocationText(),
            request.getIsShared()
        );
        // 수정 후 최신 데이터로 응답
        return CardConverter.toCardDetailResponse(card, null); // nextCardId는 수정 시점에는 null로
    }

    @Override
    @Transactional(readOnly = true)
    public CardResponse.PagedCardFeedResponseDto getCardFeedByCursor(Long userId, int size, Long cursor) {

        Slice<Card> cardSlice;
        Pageable pageable = PageRequest.of(0, size);
        if(userId == null) { // 전체 선택
            if (cursor == null) {
                cardSlice = cardRepository.findByIsDeletedFalseAndIsSharedTrueOrderByIdDesc(pageable);
            } else {
                cardSlice = cardRepository.findByIsDeletedFalseAndIsSharedTrueAndIdLessThanOrderByIdDesc(cursor, pageable);
            }
        }else if(userId == 1L){ // 내 피드 조회 todo: 로그인 유저로
            // 전체 기록
            if(cursor == null){
                cardSlice = cardRepository.findByIsDeletedFalseAndUserIdOrderByIdDesc(userId,pageable);
            }else{
                cardSlice = cardRepository.findByIsDeletedFalseAndIsSharedTrueAndUserIdAndIdLessThanOrderByIdDesc(userId,cursor,pageable);
            }
        }else{ // 선택한 사용자
            //최근 7일 기록
            LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
            if(cursor == null){
                cardSlice = cardRepository.findByIsDeletedFalseAndUserIdAndCreatedAtAfterOrderByIdDesc(
                        userId, sevenDaysAgo, pageable);
            } else {
                cardSlice = cardRepository.findByIsDeletedFalseAndUserIdAndCreatedAtAfterAndIdLessThanOrderByIdDesc(
                        userId, sevenDaysAgo, cursor, pageable);
            }
            if(cardSlice.isEmpty()){
                throw new ExceptionHandler(NO_RECENT_CARDS);
            }
        }
        if(cardSlice.isEmpty()){
            throw new ExceptionHandler(CARD_NOT_FOUND);
        }
        List<CardFeedResponse> feedList = cardSlice.stream()
                .map(card -> getCardFeed(card.getId(),userId))
                .toList();

        return CardConverter.toPagedCardFeedResponseDTto(userId,cardSlice,feedList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecommendCardResponse> getRecommendedCardPreviews() {
        List<Card> cards = cardRepository.findCardsWithReactionCountOver1();

        // 랜덤 셔플 후 최대 10개 추출
        Collections.shuffle(cards);
        return cards.stream()
            .limit(10)
            .map(card -> new RecommendCardResponse(
                card.getId(),
                card.getCardImageUrl() // 첫 이미지 URL 가져오는 메서드 필요
            ))
            .toList();
    }
}
