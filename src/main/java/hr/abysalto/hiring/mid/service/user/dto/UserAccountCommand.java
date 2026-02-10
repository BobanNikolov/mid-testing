package hr.abysalto.hiring.mid.service.user.dto;

import hr.abysalto.hiring.mid.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountCommand {
  private Long id;
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private String displayName;
  private Set<Role> roles;
}
