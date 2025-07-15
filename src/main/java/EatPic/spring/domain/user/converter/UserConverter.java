package EatPic.spring.domain.user.converter;

import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.reaction.dto.ReactionResponseDTO;
import EatPic.spring.domain.reaction.entity.ReactionType;
import EatPic.spring.domain.user.dto.UserResponseDTO;
import EatPic.spring.domain.user.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public class UserConverter {

    public static UserResponseDTO.ProfileDto toProfileIconDto(User user){
        return UserResponseDTO.ProfileDto.builder()
                .userId(user.getId())
                .profileImageUrl(user.getProfileImageUrl())
                .nameId(user.getNameId())
                .build();
    }

    public static UserResponseDTO.UserIconListResponseDto toUserIconListResponseDto(int total, int page, int size, List<UserResponseDTO.ProfileDto> pagedUserList){
        return UserResponseDTO.UserIconListResponseDto.builder()
                .total(total)
                .page(page)
                .size(size)
                .userIconList(pagedUserList)
                .build();
    }

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


}
