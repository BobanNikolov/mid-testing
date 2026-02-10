package hr.abysalto.hiring.mid.repository;

import hr.abysalto.hiring.mid.model.ProductFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFavoriteRepository extends JpaRepository<ProductFavorite, Long> {
}
