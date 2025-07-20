package EatPic.spring.domain.user.service;

import EatPic.spring.domain.user.converter.UserConverter;
import EatPic.spring.domain.user.dto.LoginRequestDTO;
import EatPic.spring.domain.user.dto.LoginResponseDTO;
import EatPic.spring.domain.user.exception.UserErrorCode;
import EatPic.spring.domain.user.exception.handler.UserHandler;
import EatPic.spring.domain.user.mapping.UserFollow;
import EatPic.spring.domain.user.repository.UserFollowRepository;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.global.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;

    private final UserFollowRepository userFollowRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public List<User> followingUser(Long userId) {
        User user = userRepository.findUserById(userId);
        List<UserFollow> followingList = userFollowRepository.findByUser(user);
        return followingList.stream().map(UserFollow::getTargetUser).collect(Collectors.toList());
    }

    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UserHandler(UserErrorCode.MEMBER_NOT_FOUND));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserHandler(UserErrorCode.INVALID_PASSWORD);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), null,
                Collections.emptyList()
                //Collections.singleton(() -> user.getRole().name())
        );

        String accessToken = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        return UserConverter.toLoginResultDTO(
                user.getId(),
                accessToken,
                refreshToken
        );
    }
}
