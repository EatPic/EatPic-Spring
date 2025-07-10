package EatPic.spring.domain.community.service;

import EatPic.spring.domain.community.UserFollow;
import EatPic.spring.domain.community.repository.UserFollowRepository;
import EatPic.spring.domain.community.repository.UserRepository;
import EatPic.spring.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserFollowRepository userFollowRepository;

    @Override
    public List<User> followingUser(Long userId) {
        User user = userRepository.findUserById(userId);
        List<UserFollow> followingList = userFollowRepository.findByUser(user);
        for(UserFollow userFollow : followingList){
            System.out.println(userFollow.getUser().getId());
        }
        return followingList.stream().map(UserFollow::getTargetUser).collect(Collectors.toList());
    }
}
