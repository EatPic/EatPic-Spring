package EatPic.spring.domain.card.service;

import EatPic.spring.domain.card.dto.request.CardCreateRequest;
import EatPic.spring.domain.card.dto.response.CardResponse;
import EatPic.spring.domain.card.entity.Card;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public interface CardService {

  private final CardRepository cardRepository;
  private final UserRepository userRepository;

  @Transactional
  public CardResponse.CreateCardResponse createNewCard(CardCreateRequest.CreateCardRequest request, Long userId) {

    // 아직 유저 관련 처리 안했음
    User user = userRepository.findUserById(userId);

    Card newcard = Card.builder()
        .isShared(request.getIsShared())
        .latitude(request.getLatitude())
        .longitude(request.getLongitude())
        .cardImageUrl(request.getCardImageUrl())
        .recipeUrl(request.getRecipeUrl())
        .memo(request.getMemo())
        .recipe(request.getRecipe())
        .meal(request.getMeal())
        .user(user)
        .build();

    Card savedCard = cardRepository.save(newcard);

    return CardResponse.CreateCardResponse.builder().newcardId(savedCard.getId()).build();
  }

}
