package EatPic.spring.domain.user.service;

import EatPic.spring.domain.user.mapping.UserFollow;
import EatPic.spring.domain.user.repository.UserFollowRepository;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;

    @Override
    public List<User> followingUser(Long userId) {
        User user = userRepository.findUserById(userId);
        List<UserFollow> followingList = userFollowRepository.findByUser(user);
        return followingList.stream().map(UserFollow::getTargetUser).collect(Collectors.toList());
    }
}
