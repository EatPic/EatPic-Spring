package EatPic.spring.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI EatpicAPI() {
        Info info = new Info()
                .title("EatpicAPI")
                .description("EatpicAPI 명세서")
                .version("1.0.0");

        return new OpenAPI()
                .info(info)
                .addServersItem(new Server().url("/"));
        // 인증 관련(SecurityRequirement, Components) 설정 모두 제거
    }

    @Bean
    public GroupedOpenApi user() {
        String[] pathsToMatch = {"/api/**"};

        return GroupedOpenApi.builder()
                .group("user")
                .displayName("일반 유저 API")
                .pathsToMatch(pathsToMatch)
                .build();
    }
}
