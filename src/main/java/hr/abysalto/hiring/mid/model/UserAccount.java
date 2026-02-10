package hr.abysalto.hiring.mid.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "user_account", schema = "abys")
public class UserAccount implements UserDetails {

  @Id
  @Column(name = "id", columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "display_name", nullable = false)
  private String displayName;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_x_role",
      schema = "abys",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  @Builder.Default
  private Set<Role> roles = new HashSet<>();

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private Cart cart;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<ProductFavorite> favorites;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }
}
