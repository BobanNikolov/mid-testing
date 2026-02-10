package hr.abysalto.hiring.mid.repository;

import hr.abysalto.hiring.mid.model.Cart;
import hr.abysalto.hiring.mid.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
  Optional<CartItem> findByCartAndProductId(Cart cart, Long productId);
}
