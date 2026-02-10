package hr.abysalto.hiring.mid.repository;

import hr.abysalto.hiring.mid.model.Cart;
import hr.abysalto.hiring.mid.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
  Optional<Cart> findByUser(UserAccount user);
}
