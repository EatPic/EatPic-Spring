package EatPic.spring.domain.user.converter;

import EatPic.spring.domain.user.dto.LoginResponseDTO;
import EatPic.spring.domain.user.dto.UserResponseDTO;
import EatPic.spring.domain.user.entity.User;

import java.util.List;

public class UserConverter {

    public static LoginResponseDTO toLoginResultDTO(Long userId, String accessToken, String refreshToken) {
        return LoginResponseDTO.builder()
                .userId(userId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static UserResponseDTO.ProfileIconDto toProfileIconDto(User user){
        return UserResponseDTO.ProfileIconDto.builder()
                .userId(user.getId())
                .profileImageUrl(user.getProfileImageUrl())
                .nameId(user.getNameId())
                .build();
    }

    public static UserResponseDTO.UserIconListResponseDto toUserIconListResponseDto(int total, int page, int size, List<UserResponseDTO.ProfileIconDto> pagedUserList){
        return UserResponseDTO.UserIconListResponseDto.builder()
                .total(total)
                .page(page)
                .size(size)
                .userIconList(pagedUserList)
                .build();
    }
}
