package EatPic.spring.domain.card.service;

import EatPic.spring.domain.card.converter.CardConverter;
import EatPic.spring.domain.card.dto.response.SearchResponseDTO;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.comment.repository.CommentRepository;
import EatPic.spring.domain.reaction.repository.ReactionRepository;
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

    @Override
    public SearchResponseDTO.GetCardListResponseDto getAllCards(int limit, Long cursor) {
        // 유저 관련 처리는 이후에..
        // 페이징 처리 하기
        Pageable pageable = PageRequest.of(0, limit + 1, Sort.by("id").ascending());
        Slice<Card> cards = cardRepository.findByCursor(cursor, pageable);

        List<SearchResponseDTO.GetCardResponseDto> result = cards.getContent().stream()
                .map(card -> CardConverter.toGetCardResponseDto(
                        card,
                        commentRepository.countAllCommentByCard(card),
                        reactionRepository.countAllReactionByCard(card)
                ))
                .toList();

        Long nextCursor = result.isEmpty() ? null : result.get(result.size() - 1).getId();
        boolean hasNext = cards.hasNext();

        return new SearchResponseDTO.GetCardListResponseDto(result, nextCursor, result.size(), hasNext);
    }
}
