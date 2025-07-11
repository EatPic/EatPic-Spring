package EatPic.spring.domain.newcard.controller;

import EatPic.spring.domain.newcard.dto.NewcardRequest;
import EatPic.spring.domain.newcard.dto.NewcardResponse;
import EatPic.spring.domain.newcard.service.NewcardCommandService;
import EatPic.spring.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/newcard")
@Tag(name = "새로운 픽카드 API")
public class NewcardController {

    private final NewcardCommandService newcardCommandService;

    @PostMapping("")
    @Operation(summary = "픽카드 기록 작성하기", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON201", description = "픽카드가 기록되었습니다.")
    })
    public BaseResponse<NewcardResponse.CreateNewcardResponse> createNewcard(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            @Valid @RequestBody NewcardRequest.CreateNewcardRequest request,
            @RequestParam(name = "userId") Long userId) {

        return BaseResponse.onSuccess(newcardCommandService.createNewcard(request));
    }
}

