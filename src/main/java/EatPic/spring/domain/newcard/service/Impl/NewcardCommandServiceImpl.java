package EatPic.spring.domain.newcard.service.Impl;

import EatPic.spring.domain.newcard.Card;
import EatPic.spring.domain.newcard.dto.NewcardRequest;
import EatPic.spring.domain.newcard.dto.NewcardResponse;
import EatPic.spring.domain.newcard.repository.NewcardRepository;
import EatPic.spring.domain.newcard.service.NewcardCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewcardCommandServiceImpl implements NewcardCommandService {
    private final NewcardRepository newcardRepository;

    @Override
    public NewcardResponse.CreateNewcardResponse createNewcard(NewcardRequest.CreateNewcardRequest request) {
        Card newcard = Card.builder()
                .isShared(request.getIsShared())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .cardImageUrl(request.getCardImageUrl())
                .recipeUrl(request.getRecipeUrl())
                .memo(request.getMemo())
                .recipe(request.getRecipe())
                .meal(request.getMeal())
                .build();

        Card savedCard = newcardRepository.save(newcard);

        return NewcardResponse.CreateNewcardResponse.builder().newcardId(savedCard.getId()).build();
    }
}
