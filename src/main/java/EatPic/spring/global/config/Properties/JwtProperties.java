package EatPic.spring.global.config.Properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.jwt")
public class JwtProperties {
    private String secret;
    private long accessTokenValidity;
    private long refreshTokenValidity;
    private int refreshTokenReissueThresholdDays;
}
