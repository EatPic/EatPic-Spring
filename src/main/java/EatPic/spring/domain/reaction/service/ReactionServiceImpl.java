package EatPic.spring.domain.reaction.service;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.reaction.dto.ReactionResponseDTO;
import EatPic.spring.domain.reaction.entity.Reaction;
import EatPic.spring.domain.reaction.entity.ReactionType;
import EatPic.spring.domain.reaction.repository.ReactionRepository;
import EatPic.spring.domain.user.converter.UserConverter;
import EatPic.spring.domain.user.dto.UserResponseDTO;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.repository.UserFollowRepository;
import EatPic.spring.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReactionServiceImpl implements ReactionService {
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final ReactionRepository reactionRepository;
    private final UserFollowRepository userFollowRepository;

    @Override
    public Reaction handleReaction(Long cardId, ReactionType reactionType) {
        User user = userRepository.findUserById(1L); //todo: 로그인한 사용자로 수정
        Card card = cardRepository.findCardById(cardId);

        Reaction reaction = Reaction.builder().user(user).card(card).reactionType(reactionType).build();

        Reaction prev = reactionRepository.findByUserAndCard(user, card);
        if (prev == null) { // 이전에 작성한 반응 없는 경우
            reactionRepository.save(reaction);
        }else if (!prev.getReactionType().equals(reactionType)) { // 반응을 변경할 경우
            prev.setReactionType(reactionType);
        }else{ // 이전에 작성한 반응을 한 번더 누를 경우(삭제)
            reactionRepository.delete(prev);
        }

        return reaction;
    }

    @Override
    public ReactionResponseDTO.CardReactionUserListDto getCardUsersByReactionType(Long cardId, ReactionType reactionType, Integer page, Integer size){
        User me = userRepository.findUserById(1L);

        Page<User> userPage = reactionRepository.findUsersByCardAndReactionType(cardId, reactionType, PageRequest.of(page,size));

        Page<UserResponseDTO.ProfileDto> profilDtoePage = userPage.map(user -> {
            boolean isFollowing = userFollowRepository.existsByUserAndTargetUser(me, user);
            return UserConverter.toProfileDto(user, isFollowing);
        });

        return UserConverter.toCardReactionUsersListDto(cardId,reactionType,profilDtoePage);
    }

}
