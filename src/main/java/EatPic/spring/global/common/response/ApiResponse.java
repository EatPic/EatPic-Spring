package EatPic.spring.global.common.response;

import EatPic.spring.global.common.ReasonDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponse<T> {
    private final ReasonDTO reason;
    private final T result;
}
