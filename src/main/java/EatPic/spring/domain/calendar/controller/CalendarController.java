package EatPic.spring.domain.calendar.controller;

import EatPic.spring.domain.calendar.dto.CalendarDayResponse;
import EatPic.spring.domain.calendar.service.CalendarService;
import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.service.UserService;
import EatPic.spring.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController  //API요청을 처리하는 컨트롤러임을 명시!
@RequiredArgsConstructor
@RequestMapping("/api/calendar")  //이 클래스에서 모든 API경로 앞에 공통적으로 붙을 주소 설정!!
@Tag(name = "Calendar", description = "캘린더 관련 API")
public class CalendarController {

  private final CalendarService calendarService;
  private final UserService userService;


  @Operation(summary = "캘린더 화면 데이터 조회",  //Operation의 구성요소 중 하나로, API에 대한 한줄요약.
      description = "캘린더에서 기록이 있는 날짜에 해당하는 대표이미지를 불러오는 API") //상세설명
  @GetMapping
  public ApiResponse<List<CalendarDayResponse>> getCalendar(
      //@AuthenticationPrincipal CustomUserDetails userDetails,
      HttpServletRequest request,
      @RequestParam int year,
      @RequestParam int month
  ) {
    User user = userService.getLoginUser(request);
    //Long userId = 1L; //Long userId = userDetails.getUser().getId(); //로그인 구현 시 이렇게 바꾸기
    //User user = userDetails.getUser(); //아니면 이렇게 해서 객체 자체를 넘기기 (이게 일반적인 방법)
    return ApiResponse.onSuccess(calendarService.getCalendar(user, year, month));
  }



}
