package EatPic.spring.domain.user.converter;

import EatPic.spring.domain.card.dto.response.SearchResponseDTO;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.reaction.dto.ReactionResponseDTO;
import EatPic.spring.domain.reaction.entity.ReactionType;
import EatPic.spring.domain.user.dto.response.UserResponseDTO;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.mapping.UserBlock;
import EatPic.spring.domain.user.mapping.UserFollow;
import org.springframework.data.domain.Page;

public class UserConverter {
    public static UserResponseDTO.ProfileIconDto toProfileIconDto(User user) {
        return UserResponseDTO.ProfileIconDto.builder()
                .userId(user.getId())
                .profileImageUrl(user.getProfileImageUrl())
                .nameId(user.getNameId())
                .nickname(user.getNickname())
                .build();
    }

    public static UserResponseDTO.ProfileDto toProfileDto(User user, Boolean isFollowing) {
        return UserResponseDTO.ProfileDto.builder()
                .userId(user.getId())
                .profileImageUrl(user.getProfileImageUrl())
                .nameId(user.getNameId())
                .nickname(user.getNickname())
                .isFollowing(isFollowing)
                .build();
    }
    
    public static UserResponseDTO.DetailProfileDto toDetailProfileDto(
        User user, 
        Boolean isFollowing, 
        Long totalCard, 
        Long totalFollower, 
        Long totalFollowing) {
    
    return UserResponseDTO.DetailProfileDto.builder()
            .userId(user.getId())
            .profileImageUrl(user.getProfileImageUrl())
            .nameId(user.getNameId())
            .nickname(user.getNickname())
            .isFollowing(isFollowing)
            .introduce(user.getIntroduce())
            .totalCard(totalCard)
            .totalFollower(totalFollower)
            .totalFollowing(totalFollowing)
            .build();
}

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
                .nameId(user.getNameId())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }

    public static UserResponseDTO.UserBlockResponseDto toUserBlockResponseDto(UserBlock userBlock) {
        return UserResponseDTO.UserBlockResponseDto.builder()
                .userId(userBlock.getUser().getId())
                .targetUserId(userBlock.getBlockedUser().getId())
                .build();
    }
}