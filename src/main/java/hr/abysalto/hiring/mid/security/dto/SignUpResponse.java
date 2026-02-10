package hr.abysalto.hiring.mid.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Metadata for sign up response")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponse {
  @Schema(description = "Response message", example = "Signed up successfully.")
  private String message;
  @Schema(description = "Response token", example = "8c82e6c4-b3c4-413a-8dae-01a4f3a0e585")
  private String token;
}
