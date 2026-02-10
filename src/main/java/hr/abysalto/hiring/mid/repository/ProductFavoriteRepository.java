package hr.abysalto.hiring.mid.repository;

import hr.abysalto.hiring.mid.model.ProductFavorite;
import hr.abysalto.hiring.mid.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductFavoriteRepository extends JpaRepository<ProductFavorite, Long> {
  boolean existsByUserAndProductId(UserAccount user, Long productId);

  List<ProductFavorite> findByUser(UserAccount user);
}
