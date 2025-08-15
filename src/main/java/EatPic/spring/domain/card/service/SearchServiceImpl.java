package EatPic.spring.domain.card.service;

import EatPic.spring.domain.card.converter.CardConverter;
import EatPic.spring.domain.card.dto.response.SearchResponseDTO;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.card.repository.HashtagRepository;
import EatPic.spring.domain.comment.repository.CommentRepository;
import EatPic.spring.domain.hashtag.entity.Hashtag;
import EatPic.spring.domain.reaction.repository.ReactionRepository;
import EatPic.spring.domain.reaction.service.ReactionService;
import EatPic.spring.domain.user.converter.UserConverter;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.repository.UserFollowRepository;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.domain.user.service.UserService;
import EatPic.spring.global.common.code.status.ErrorStatus;
import EatPic.spring.global.common.exception.handler.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;      // 자동으로 생성자 주입
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;
    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;
    private final HashtagRepository hashtagRepository;
    private final UserService userService;
    private final CardServiceImpl cardService;
    private final ReactionService reactionService;

    // 탐색 탭에서 모든 유저 리스트 조회
    @Override
    public SearchResponseDTO.GetCardListResponseDto getAllCards(HttpServletRequest request, int limit, Long cursor) {
        // 유저 관련 처리는 이후에..
        // 페이징 처리 하기
        Pageable pageable = PageRequest.of(0, limit + 1, Sort.by("id").ascending());
        Slice<Card> cards = cardRepository.findByCursor(cursor, pageable);

        List<Long> cardIds = cards.stream().map(Card::getId).toList();
        Map<Long, Integer> commentCountMap = cardService.getCommentCountMap(cardIds);
        Map<Long, Integer> reactionCountMap = cardService.getReactionCountMap(cardIds);

        List<SearchResponseDTO.GetCardResponseDto> result = cards.getContent().stream()
                .map(card -> CardConverter.toGetCardResponseDto(
                        card,
                        commentCountMap.getOrDefault(card.getId(), 0),
                        reactionCountMap.getOrDefault(card.getId(), 0)
                ))
                .toList();

        boolean hasNext = cards.hasNext();
        Long nextCursor = hasNext ? cards.getContent().get(cards.getContent().size() - 1).getId() : null;

        return new SearchResponseDTO.GetCardListResponseDto(result, nextCursor, result.size(), hasNext);
    }

    // 검색 범위가 전체인 경우에서 계정 검색
    @Override
    public SearchResponseDTO.GetAccountListResponseDto getAccountInAll(HttpServletRequest request, String query, int limit, Long cursor) {

        User user = userService.getLoginUser(request);

        // 페이징 처리 하기
        Pageable pageable = PageRequest.of(0, limit + 1, Sort.by("id").ascending());
        Slice<User> users = userRepository.searchAccountInAll(query, cursor, pageable);

        // 검색 결과가 없으면 예외 발생
        if (users.isEmpty()) {
            throw new ExceptionHandler(ErrorStatus._NO_RESULTS_FOUND);
        }

        List<SearchResponseDTO.GetAccountResponseDto> result = users.getContent().stream()
                .map(UserConverter::toAccountDto)
                .toList();

        boolean hasNext = users.hasNext();
        Long nextCursor = hasNext ? users.getContent().get(users.getContent().size() - 1).getId() : null;

        return new SearchResponseDTO.GetAccountListResponseDto(result, nextCursor, result.size(), hasNext);

    }

    // 검색범위가 유저가 팔로우한 사용자인 경우에서 계정 검색
    @Override
    public SearchResponseDTO.GetAccountListResponseDto getAccountInFollow(HttpServletRequest request, String query, int limit, Long cursor) {
        Pageable pageable = PageRequest.of(0, limit + 1, Sort.by("id").ascending());

        User user = userService.getLoginUser(request);

        // loginUserId가 팔로우한 사람 중에서, query 조건으로
        Slice<User> users = userRepository.searchAccountInFollow(query, cursor, pageable, user.getId());

        if (users.isEmpty()) {
            throw new ExceptionHandler(ErrorStatus._NO_RESULTS_FOUND);
        }

        List<SearchResponseDTO.GetAccountResponseDto> result = users.getContent().stream()
                .map(UserConverter::toAccountDto)
                .toList();

        boolean hasNext = users.hasNext();
        Long nextCursor = hasNext ? users.getContent().get(users.getContent().size() - 1).getId() : null;

        return new SearchResponseDTO.GetAccountListResponseDto(result, nextCursor, result.size(), hasNext);

    }

    // 검색범위가 전체인 경우 해시태그 검색
    @Override
    public SearchResponseDTO.GetHashtagListResponseDto getHashtagInAll(HttpServletRequest request, String query, int limit, Long cursor) {

        User user = userService.getLoginUser(request);

        Pageable pageable = PageRequest.of(0, limit + 1, Sort.by("id").ascending());
        Slice<Hashtag> hashtags = hashtagRepository.searchHashtagInAll("%" + query + "%", cursor, pageable);

        return getGetHashtagListResponseDto(hashtags);
    }
    // 유저가 팔로우한 사용자인 경우에서 해시태그 검색
    @Override
    public SearchResponseDTO.GetHashtagListResponseDto getHashtagInFollow(HttpServletRequest request, String query, int limit, Long cursor) {

        User user = userService.getLoginUser(request);

        // 팔로우한 유저 목록 조회
        List<Long> followingUserIds = userFollowRepository.findFollowingUserIds(user.getId());
        if (followingUserIds == null || followingUserIds.isEmpty()) {
            throw new ExceptionHandler(ErrorStatus._NO_RESULTS_FOUND); // 팔로잉한 유저가 없는 경우
        }

        Pageable pageable = PageRequest.of(0, limit + 1, Sort.by("id").ascending());
        Slice<Hashtag> hashtags = hashtagRepository.searchHashtagInFollow(query, followingUserIds, cursor, pageable);

        return getGetHashtagListResponseDto(hashtags);
    }

    private SearchResponseDTO.GetHashtagListResponseDto getGetHashtagListResponseDto(Slice<Hashtag> hashtags) {
        List<Long> hashtagsIds = hashtags.stream().map(Hashtag::getId).toList();
        Map<Long,Long> cardCountByHashtagMap = getMapCardCountByHashtag(hashtagsIds);

        List<SearchResponseDTO.GetHashtagResponseDto> result = hashtags.getContent().stream()
                .map(hashtag -> CardConverter.toHashtagDto(
                        hashtag,
                        cardCountByHashtagMap.getOrDefault(hashtag.getId(),0L)
                ))
                .filter(dto -> dto.getCard_count() > 0)  // 카드가 없는 해시태그는 제외
                .toList();

        if (result.isEmpty()) {
            throw new ExceptionHandler(ErrorStatus._NO_RESULTS_FOUND);

        }
        boolean hasNext = hashtags.hasNext();
        Long nextCursor = hasNext ? hashtags.getContent().get(hashtags.getContent().size() - 1).getId() : null;

        return new SearchResponseDTO.GetHashtagListResponseDto(result, nextCursor, result.size(), hasNext);
    }


    // 해시태그 선택 시 해당 해시태그가 포함된 픽카드 리스트 조회
    @Override
    public SearchResponseDTO.GetCardListResponseDto getCardsByHashtag(HttpServletRequest request, Long hashtagId, int limit, Long cursor) {

        User user = userService.getLoginUser(request);

        Pageable pageable = PageRequest.of(0, limit + 1, Sort.by("id").ascending());
        Slice<Card> cards = cardRepository.findCardsByHashtag(hashtagId, cursor, pageable);

        List<Long> cardIds = cards.stream().map(Card::getId).toList();
        Map<Long, Integer> commentCountMap = cardService.getCommentCountMap(cardIds);
        Map<Long, Integer> reactionCountMap = cardService.getReactionCountMap(cardIds);

        List<SearchResponseDTO.GetCardResponseDto> content = cards.getContent().stream()
                .map(card -> {
                    int commentCount = commentCountMap.getOrDefault(card.getId(), 0);
                    int reactionCount = reactionCountMap.getOrDefault(card.getId(), 0);
                    return CardConverter.toCardResponseDto(card, commentCount, reactionCount);
                })
                .toList();

        Long nextCursor = content.size() > limit ? content.get(limit).getId() : null;

        boolean hasNext = content.size() > limit;
        if (hasNext) {
            content = content.subList(0, limit); // limit+1 로 가져온 마지막 요소는 잘라줌
        }

        return new SearchResponseDTO.GetCardListResponseDto(content, nextCursor, content.size(), hasNext);
    }

    private Map<Long, Long> getMapCardCountByHashtag(List<Long> hashtagIds){
        List<Object[]> counts = cardRepository.countCardsByHashtagIds(hashtagIds);
        return counts.stream().collect(Collectors.toMap(
                        row -> (Long) row[0],   // hashtagId
                        row -> (Long) row[1]    // count
                ));
    }
}