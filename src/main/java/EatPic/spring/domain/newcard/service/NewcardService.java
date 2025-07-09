package EatPic.spring.domain.newcard.service;

import EatPic.spring.domain.newcard.dto.NewcardRequest;
import EatPic.spring.domain.newcard.dto.NewcardResponse;

public interface NewcardService {

    // 픽카드 기록하기 생성
    NewcardResponse.CreateNewcardResponse createNewcard(NewcardRequest.CreateNewcardRequest request);
}
