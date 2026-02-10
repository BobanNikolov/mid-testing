package hr.abysalto.hiring.mid.service.product;

import hr.abysalto.hiring.mid.service.product.dto.ProductResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
  List<ProductResponse> getProducts(Pageable pageable);

  ProductResponse getProduct(Long id);

  List<ProductResponse> addFavoriteProducts(List<Long> productIds);
}
