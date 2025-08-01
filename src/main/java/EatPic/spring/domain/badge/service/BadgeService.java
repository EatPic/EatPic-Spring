package EatPic.spring.domain.badge.service;

import EatPic.spring.domain.badge.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BadgeService {

  private final BadgeRepository badgeRepository;


}
