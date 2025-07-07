package EatPic.spring.global.common;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
public class TestCheckController {

    @GetMapping("/test")
    public String testCheck() {
        return "test check";
    }
}
