package EatPic.spring.domain.greetingMessage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record GreetingResponse(
        @Schema(description = "닉네임", example = "잇콩이")
        @NotNull
        String nickname,
        @Schema(description = "메세지", example = "오늘도 Pic 카드 기록을 시작해볼까요?")
        @NotNull
        String message) {

}
