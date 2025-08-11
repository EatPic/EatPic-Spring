package EatPic.spring.domain.reaction.service;

import EatPic.spring.domain.badge.entity.ConditionType;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.reaction.converter.ReactionConverter;
import EatPic.spring.domain.reaction.dto.ReactionResponseDTO;
import EatPic.spring.domain.reaction.entity.Reaction;
import EatPic.spring.domain.reaction.entity.ReactionType;
import EatPic.spring.domain.reaction.repository.ReactionRepository;
import EatPic.spring.domain.user.converter.UserConverter;
import EatPic.spring.domain.user.dto.response.UserResponseDTO;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.repository.UserBadgeRepository;
import EatPic.spring.domain.user.repository.UserFollowRepository;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.domain.user.service.UserBadgeService;
import EatPic.spring.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReactionServiceImpl implements ReactionService {
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final ReactionRepository reactionRepository;
    private final UserFollowRepository userFollowRepository;
    private final UserBadgeService userBadgeService;
    private final UserService userService;
    // findBy~ 자주 사용 -> 하나의 메서드로
    private User getUser(Long userId){
        return userRepository.findUserById(userId);
    }

    @Override
    public ReactionResponseDTO.ReactionHandleResponseDto handleReaction(HttpServletRequest request, Long cardId, ReactionType reactionType) {
        User user = userService.getLoginUser(request);
        Card card = cardRepository.findCardById(cardId);

        Reaction reaction = Reaction.builder().user(user).card(card).reactionType(reactionType).build();

        Reaction prev = reactionRepository.findByUserAndCard(user, card);
        if (prev == null) { // 이전에 작성한 반응 없는 경우
            reactionRepository.save(reaction);
            // 공감짱 뱃지 처리
            userBadgeService.checkAndAssignBadges(user, ConditionType.REACTION_GIVEN, 1);
            // 사진 장인 뱃지 처리
            User cardAuthor = card.getUser();
            userBadgeService.checkAndAssignBadges(cardAuthor, ConditionType.CARD_LIKES_RECEIVED, 1);
            return ReactionConverter.reactionToReactionHandleResponseDTO(reaction,"add");
        }else if (!prev.getReactionType().equals(reactionType)) { // 반응을 변경할 경우
            prev.setReactionType(reactionType);
            return ReactionConverter.reactionToReactionHandleResponseDTO(reaction,"change");
        }else{ // 이전에 작성한 반응을 한 번더 누를 경우(삭제)
            reactionRepository.delete(prev);
            return ReactionConverter.reactionToReactionHandleResponseDTO(reaction,"delete");
        }
    }

    @Override
    public ReactionResponseDTO.CardReactionUserListDto getCardUsersByReactionType(HttpServletRequest request,Long cardId, ReactionType reactionType, Integer page, Integer size){
        User me = userService.getLoginUser(request);

        Page<User> userPage = reactionRepository.findUsersByCardAndReactionType(cardId, reactionType, PageRequest.of(page,size));

        Page<UserResponseDTO.ProfileDto> profilDtoePage = userPage.map(user -> {
            boolean isFollowing = userFollowRepository.existsByUserAndTargetUser(me, user);
            return UserConverter.toProfileDto(user, isFollowing);
        });

        return UserConverter.toCardReactionUsersListDto(cardId,reactionType,profilDtoePage);
    }

}
