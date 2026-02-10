package hr.abysalto.hiring.mid.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "token")
@Configuration
@Data
public class TokenProperties {
  private String secret;

  private Long expirationMs;
}
