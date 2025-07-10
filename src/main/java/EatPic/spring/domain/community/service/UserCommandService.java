package EatPic.spring.domain.community.service;

import EatPic.spring.domain.user.User;

import java.util.List;

public interface UserCommandService {
    List<User> followingUser(Long userId);
}
