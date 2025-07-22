package EatPic.spring.domain.user.service;

import EatPic.spring.domain.user.converter.UserConverter;
import EatPic.spring.domain.user.dto.UserInfoDTO;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.exception.UserErrorCode;
import EatPic.spring.domain.user.exception.handler.UserHandler;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.global.config.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional(readOnly = true)
    public UserInfoDTO getUserInfo(HttpServletRequest request) {
        Authentication authentication = jwtTokenProvider.extractAuthentication(request);
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserHandler(UserErrorCode.MEMBER_NOT_FOUND));
        return UserConverter.toUserInfoDTO(user);
    }
}

