package hr.abysalto.hiring.mid.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "cart", schema = "abys")
public class Cart extends BaseEntity {
  @Id
  @Column(name = "id", columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id")
  private UserAccount user;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
  private List<CartItem> items;
}
