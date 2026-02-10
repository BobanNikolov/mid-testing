package hr.abysalto.hiring.mid.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Metadata for sign in request")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
  @Schema(description = "Username of a user", example = "johndoe")
  private String username;
  @Schema(description = "Password of a user", example = "test123")
  private String password;
}
