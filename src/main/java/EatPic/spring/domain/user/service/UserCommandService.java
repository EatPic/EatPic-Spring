package EatPic.spring.domain.user.service;

import EatPic.spring.domain.user.entity.User;

import java.util.List;

public interface UserCommandService {
    List<User> followingUser(Long userId);
}
