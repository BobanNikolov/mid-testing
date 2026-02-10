package hr.abysalto.hiring.mid.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
    name = SwaggerConfiguration.BEARER_AUTHENTICATION,
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
@OpenAPIDefinition(info = @Info(
    title = "Abysalto Mid Java Software Engineer Testing"))
public class SwaggerConfiguration {

  public static final String BEARER_AUTHENTICATION = "Bearer Authentication";

  /**
   * Customize open api open api.
   *
   * @return the open api#
   * @see https://www.baeldung.com/openapi-jwt-authentication#3-global-configuration
   */
  @Bean
  public OpenAPI customizeOpenAPI() {
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement()
            .addList(BEARER_AUTHENTICATION))
        .components(new Components()
            .addSecuritySchemes(BEARER_AUTHENTICATION,
                new io.swagger.v3.oas.models.security.SecurityScheme()
                    .name(BEARER_AUTHENTICATION)
                    .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));
  }

}
