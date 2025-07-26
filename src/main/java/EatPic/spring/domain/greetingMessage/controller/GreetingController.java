package EatPic.spring.domain.greetingMessage.controller;

import EatPic.spring.domain.greetingMessage.dto.GreetingResponse;
import EatPic.spring.domain.greetingMessage.service.GreetingService;
import EatPic.spring.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/greeting")
@Tag(name = "Greeting", description = "인사말 관련 API")
public class GreetingController {

  private final GreetingService greetingService;

  @Operation(summary = "홈화면 인사말 조회",
      description = "홈화면에 접근할 때, 요청되는 API")
  @GetMapping
  public ApiResponse<GreetingResponse> getGreeting() {
    GreetingResponse response = greetingService.getGreeting();
    return ApiResponse.onSuccess(response);
  }

}
