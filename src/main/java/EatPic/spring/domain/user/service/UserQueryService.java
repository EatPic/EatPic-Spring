package EatPic.spring.domain.user.service;

import EatPic.spring.domain.user.dto.UserInfoDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface UserQueryService {
    UserInfoDTO getUserInfo(HttpServletRequest request);
}
