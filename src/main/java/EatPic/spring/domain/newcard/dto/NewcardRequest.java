package EatPic.spring.domain.newcard.dto;


import EatPic.spring.domain.card.entity.Meal;
import lombok.Getter;

import java.math.BigDecimal;

public class NewcardRequest {

    // 픽카드 기록 작성하기
    @Getter
    public static class CreateNewcardRequest {
        private Boolean isShared;           // 공개 여부
        private BigDecimal latitude;            // 위도
        private BigDecimal longitude;           // 경도
        private String cardImageUrl;           // 카드 이미지 URL
        private String recipeUrl;           // 레시피 URL
        private String memo;                // 메모
        private String recipe;              // 레시피 내용
        private Meal meal;                // 식사 종류 (breakfast, dinner, lunch, snack)
    }
}
