package EatPic.spring.domain.card.service;

import EatPic.spring.domain.card.converter.CardConverter;
import EatPic.spring.domain.card.dto.response.SearchResponseDTO;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.comment.repository.CommentRepository;
import EatPic.spring.domain.reaction.repository.ReactionRepository;
import EatPic.spring.domain.user.converter.UserConverter;
import EatPic.spring.domain.user.entity.User;
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
            throw new ExceptionHandler(ErrorStatus._NO_RESULTS_FOUND); // ErrorStatus에 NO_RESULTS_FOUND 추가 필요
        }

        List<SearchResponseDTO.GetAccountResponseDto> result = users.getContent().stream()
                .map(UserConverter::toAccountDto)
                .toList();

        Long nextCursor = result.isEmpty() ? null : result.get(result.size() - 1).getUserId();
        boolean hasNext = users.hasNext();

        return new SearchResponseDTO.GetAccountListResponseDto(result, nextCursor, result.size(), hasNext);

    }

    @Override
    public SearchResponseDTO.GetAccountListResponseDto getAccountInFollow(Long userId, String query, int limit, Long cursor) {
        // 유저 관련 처리는 이후에...
        // 페이징 처리 하기
        Pageable pageable = PageRequest.of(0, limit + 1, Sort.by("id").ascending());
        Slice<User> users = userRepository.searchAccountInAll(query, cursor, pageable);

        // 검색 결과가 없으면 예외 발생
        if (users.isEmpty()) {
            throw new ExceptionHandler(ErrorStatus._NO_RESULTS_FOUND); // ErrorStatus에 NO_RESULTS_FOUND 추가 필요
        }

        List<SearchResponseDTO.GetAccountResponseDto> result = users.getContent().stream()
                .map(UserConverter::toAccountDto)
                .toList();

        Long nextCursor = result.isEmpty() ? null : result.get(result.size() - 1).getUserId();
        boolean hasNext = users.hasNext();

        return new SearchResponseDTO.GetAccountListResponseDto(result, nextCursor, result.size(), hasNext);
    }
}
