package EatPic.spring.domain.card.service;

import EatPic.spring.domain.card.dto.response.CardResponse;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.repository.SearchRepository;
import EatPic.spring.domain.comment.repository.CommentRepository;
// import EatPic.spring.domain.reaction.Repository.ReactionRepository;
import lombok.RequiredArgsConstructor;      // 자동으로 생성자 주입
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl extends SearchService {
    private final SearchRepository searchRepository;
    private final CommentRepository commentRepository;
    // private final ReactionRepository reactionRepository;

    @Override
    public CardResponse.GetCardListResponseDto getAllCards(int limit, Long cursor) {
        // 유저 관련 처리는 이후에..
        // 페이징 처리 하기
        Pageable pageable = PageRequest.of(0, limit, Sort.by("id").ascending());
        List<Card> cards;
        if (cursor == null || cursor == 0) {
            cards = searchRepository.findAll(pageable).getContent();
        } else {
            cards = searchRepository.findByCursor(cursor, pageable);
        }

        List<CardResponse.GetCardResponseDto> result = cards.stream()
                .map(card -> CardResponse.GetCardResponseDto.builder()
                        .id(card.getId())
                        .cardImageUrl(card.getCardImageUrl())
                        // 댓글 개수 & 반응 개수 세기
                        .commentCount(commentRepository.countAllCommentByCard(card))
                        // .reactionCount(reactionRepository.countAllReactionByCard(card))
                        .build())
                .toList();

        Long nextCursor = result.isEmpty() ? null : result.get(result.size() - 1).getId();
        boolean hasNext = result.size() == limit;

        return new CardResponse.GetCardListResponseDto(result, nextCursor, result.size(), hasNext);
    }
}
