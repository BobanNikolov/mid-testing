package hr.abysalto.hiring.mid.service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDto {

  private Long id;
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private String displayName;
  private List<String> roles;
}
