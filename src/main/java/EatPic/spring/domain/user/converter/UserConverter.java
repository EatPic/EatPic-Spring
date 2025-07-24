package EatPic.spring.domain.user.converter;

import EatPic.spring.domain.card.dto.response.SearchResponseDTO;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.reaction.dto.ReactionResponseDTO;
import EatPic.spring.domain.reaction.entity.ReactionType;
import EatPic.spring.domain.user.dto.UserResponseDTO;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.mapping.UserFollow;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    public static UserResponseDTO.UserIconListResponseDto toUserIconListResponseDto(Page<UserFollow> followingPage){
        return UserResponseDTO.UserIconListResponseDto.builder()
                .total((int)followingPage.getTotalElements())
                .page(followingPage.getNumber()+1)
                .size(followingPage.getSize())
                .userIconList(followingPage.stream()
                        .map(UserFollow::getTargetUser)
                        .map(UserConverter::toProfileIconDto)
                        .toList())
                .build();
    }

    public static UserResponseDTO.ProfileDto toProfileIconDto(User user){
        return UserResponseDTO.ProfileDto.builder()
                .userId(user.getId())
                .profileImageUrl(user.getProfileImageUrl())
                .nameId(user.getNameId())
                .isFollowing(true)
                .build();
    }
    // todo: 두개 비슷함 -> 합치기
    public static UserResponseDTO.ProfileDto toProfileDto(User user, Boolean isFollowing){
        return UserResponseDTO.ProfileDto.builder()
                .userId(user.getId())
                .profileImageUrl(user.getProfileImageUrl())
                .nameId(user.getNameId())
                .nickname(user.getNickname())
                .isFollowing(isFollowing)
                .build();
    }

    public static ReactionResponseDTO.CardReactionUserListDto toCardReactionUsersListDto(Long cardId, ReactionType reactionType, Page<UserResponseDTO.ProfileDto> profileList){
        return ReactionResponseDTO.CardReactionUserListDto.builder()
                .cardId(cardId)
                .reactionType(reactionType)
                .page(profileList.getNumber()+1)
                .size(profileList.getSize())
                .total((int) profileList.getTotalElements())
                .userList(profileList.getContent())
                .build();
    }

    // 사용자 계정 받아오기
    public static SearchResponseDTO.GetAccountResponseDto toAccountDto(User user) {
        return SearchResponseDTO.GetAccountResponseDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
