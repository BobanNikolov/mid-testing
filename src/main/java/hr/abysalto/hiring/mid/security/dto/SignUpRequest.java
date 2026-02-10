package hr.abysalto.hiring.mid.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Metadata for sign up request")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

  @Schema(description = "First name of a user", example = "John")
  private String firstName;
  @Schema(description = "Last name of a user", example = "Doe")
  private String lastName;
  @Schema(description = "Email of a user", example = "john.doe@gmail.com")
  private String email;
  @Schema(description = "Username of a user", example = "johndoe")
  private String username;
  @Schema(description = "Password of a user", example = "test123")
  private String password;
}
