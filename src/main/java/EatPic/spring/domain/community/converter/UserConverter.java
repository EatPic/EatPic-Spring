package EatPic.spring.domain.community.converter;

import EatPic.spring.domain.community.dto.UserResponseDTO;
import EatPic.spring.domain.user.User;

public class UserConverter {

    public static UserResponseDTO.ProfileIconDto toProfileIconDto(User user){
        return UserResponseDTO.ProfileIconDto.builder()
                .userId(user.getId())
                .profileImageUrl(user.getProfileImageUrl())
                .nameId(user.getNameId())
                .build();
    }
}
