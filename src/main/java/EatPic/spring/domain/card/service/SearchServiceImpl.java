package EatPic.spring.domain.card.service;

import EatPic.spring.domain.card.converter.CardConverter;
import EatPic.spring.domain.card.dto.response.SearchResponseDTO;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.card.repository.HashtagRepository;
import EatPic.spring.domain.comment.repository.CommentRepository;
import EatPic.spring.domain.hashtag.entity.Hashtag;
import EatPic.spring.domain.reaction.repository.ReactionRepository;
import EatPic.spring.domain.user.converter.UserConverter;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.repository.UserFollowRepository;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.global.common.code.status.ErrorStatus;
import EatPic.spring.global.common.exception.handler.ExceptionHandler;
import lombok.RequiredArgsConstructor;      // 자동으로 생성자 주입
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;
    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;
    private final HashtagRepository hashtagRepository;

    // 탐색 탭에서 모든 유저 리스트 조회
    @Override
    public SearchResponseDTO.GetCardListResponseDto getAllCards(int limit, Long cursor) {
        // 유저 관련 처리는 이후에..
        // 페이징 처리 하기
        Pageable pageable = PageRequest.of(0, limit + 1, Sort.by("id").ascending());
        Slice<Card> cards = cardRepository.findByCursor(cursor, pageable);

        List<SearchResponseDTO.GetCardResponseDto> result = cards.getContent().stream()
                .map(card -> CardConverter.toGetCardResponseDto(
                        card,
                        commentRepository.countAllByCard(card),
                        reactionRepository.countAllReactionByCard(card)
                ))
                .toList();

        Long nextCursor = result.isEmpty() ? null : result.get(result.size() - 1).getId();
        boolean hasNext = cards.hasNext();

        return new SearchResponseDTO.GetCardListResponseDto(result, nextCursor, result.size(), hasNext);
    }

    // 검색 범위가 전체인 경우에서 계정 검색
    @Override
    public SearchResponseDTO.GetAccountListResponseDto getAccountInAll(String query, int limit, Long cursor) {
        // 유저 관련 처리는 이후에...
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

        Long nextCursor = result.isEmpty() ? null : result.get(result.size() - 1).getUserId();
        boolean hasNext = users.hasNext();

        return new SearchResponseDTO.GetAccountListResponseDto(result, nextCursor, result.size(), hasNext);

    }

    // 검색범위가 유저가 팔로우한 사용자인 경우에서 계정 검색
    @Override
    public SearchResponseDTO.GetAccountListResponseDto getAccountInFollow(String query, int limit, Long cursor, Long userId) {
        Pageable pageable = PageRequest.of(0, limit + 1, Sort.by("id").ascending());
        // loginUserId가 팔로우한 사람 중에서, query 조건으로
        Slice<User> users = userRepository.searchAccountInFollow(query, cursor, pageable, userId);

        if (users.isEmpty()) {
            throw new ExceptionHandler(ErrorStatus._NO_RESULTS_FOUND);
        }

        List<SearchResponseDTO.GetAccountResponseDto> result = users.getContent().stream()
                .map(UserConverter::toAccountDto)
                .toList();

        Long nextCursor = result.isEmpty() ? null : result.get(result.size() - 1).getUserId();
        boolean hasNext = users.hasNext();

        return new SearchResponseDTO.GetAccountListResponseDto(result, nextCursor, result.size(), hasNext);

    }

    // 검색범위가 전체인 경우 해시태그 검색
    @Override
    public SearchResponseDTO.GetHashtagListResponseDto getHashtagInAll(String query, int limit, Long cursor) {
        Pageable pageable = PageRequest.of(0, limit + 1, Sort.by("id").ascending());
        Slice<Hashtag> hashtags = hashtagRepository.searchHashtagInAll("%" + query + "%", cursor, pageable);

        List<SearchResponseDTO.GetHashtagResponseDto> result = hashtags.getContent().stream()
                .map(hashtag -> CardConverter.toHashtagDto(
                        hashtag,
                        cardRepository.countCardsByHashtag(hashtag.getId())
                ))
                .filter(dto -> dto.getCard_count() > 0)  // 카드가 없는 해시태그는 제외
                .toList();

        if (result.isEmpty()) {
            throw new ExceptionHandler(ErrorStatus._NO_RESULTS_FOUND);

        }
        Long nextCursor = result.isEmpty() ? null : result.get(result.size() - 1).getHashtagId();
        boolean hasNext = hashtags.hasNext();

        return new SearchResponseDTO.GetHashtagListResponseDto(result, nextCursor, result.size(), hasNext);
    }

    // 유저가 팔로우한 사용자인 경우에서 해시태그 검색
    @Override
    public SearchResponseDTO.GetHashtagListResponseDto getHashtagInFollow(String query, int limit, Long cursor, Long userId) {
        // 팔로우한 유저 목록 조회
        List<Long> followingUserIds = userFollowRepository.findFollowingUserIds(userId);
        if (followingUserIds == null || followingUserIds.isEmpty()) {
            throw new ExceptionHandler(ErrorStatus._NO_RESULTS_FOUND); // 팔로잉한 유저가 없는 경우
        }

        Pageable pageable = PageRequest.of(0, limit + 1, Sort.by("id").ascending());

        Slice<Hashtag> hashtags = hashtagRepository.searchHashtagInFollow(query, followingUserIds, cursor, pageable);

        List<SearchResponseDTO.GetHashtagResponseDto> result = hashtags.getContent().stream()
                .map(hashtag -> CardConverter.toHashtagDto(
                        hashtag,
                        cardRepository.countCardsByHashtag(hashtag.getId())
                ))
                .filter(dto -> dto.getCard_count() > 0)
                .toList();

        if (result.isEmpty()) {
            throw new ExceptionHandler(ErrorStatus._NO_RESULTS_FOUND);
        }

        Long nextCursor = result.isEmpty() ? null : result.get(result.size() - 1).getHashtagId();
        boolean hasNext = hashtags.hasNext();

        return new SearchResponseDTO.GetHashtagListResponseDto(result, nextCursor, result.size(), hasNext);
    }

    // 해시태그 선택 시 해당 해시태그가 포함된 픽카드 리스트 조회
    @Override
    public SearchResponseDTO.GetCardListResponseDto getCardsByHashtag(Long hashtagId, int limit, Long cursor) {
        Pageable pageable = PageRequest.of(0, limit + 1, Sort.by("id").ascending());
        Slice<Card> cards = cardRepository.findCardsByHashtag(hashtagId, cursor, pageable);

        List<SearchResponseDTO.GetCardResponseDto> content = cards.getContent().stream()
                .map(card -> {
                    int commentCount = commentRepository.countByCardId(card.getId());
                    int reactionCount = reactionRepository.countByCardId(card.getId());
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
}