package hr.abysalto.hiring.mid.service.product.impl;

import hr.abysalto.hiring.mid.feign.DummyJsonClient;
import hr.abysalto.hiring.mid.model.ProductFavorite;
import hr.abysalto.hiring.mid.model.UserAccount;
import hr.abysalto.hiring.mid.repository.ProductFavoriteRepository;
import hr.abysalto.hiring.mid.service.product.ProductService;
import hr.abysalto.hiring.mid.service.product.dto.ProductDto;
import hr.abysalto.hiring.mid.service.product.dto.ProductResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

  private final ProductFavoriteRepository productFavoriteRepository;
  private final DummyJsonClient dummyJsonClient;
  private final ConversionService conversionService;

  @Override
  @Cacheable(value = "products", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
  public List<ProductResponse> getProducts(Pageable pageable) {
    LOGGER.debug("getProducts - START - pageable: {}", pageable);

    int skip = pageable.getPageNumber() * pageable.getPageSize();
    int limit = pageable.getPageSize();
    LOGGER.debug("getProducts - calculated skip: {}, limit: {}", skip, limit);

    List<ProductDto> products = dummyJsonClient.getProducts(limit, skip).getProducts();
    LOGGER.debug("getProducts - retrieved products from DummyJSON: {}", products);

    final var productResponses = products.stream()
        .map(it -> conversionService.convert(it, ProductResponse.class))
        .filter(Objects::nonNull)
        .toList();
    LOGGER.debug("getProducts - converted product responses: {}", productResponses);

    return productResponses;
  }

  @Override
  @Cacheable(value = "product", key = "#id")
  public ProductResponse getProduct(Long id) {
    LOGGER.debug("getProduct - START - id: {}", id);

    ProductDto product = dummyJsonClient.getProductById(id);
    LOGGER.debug("getProduct - retrieved product from DummyJSON: {}", product);

    final var productResponse = conversionService.convert(product, ProductResponse.class);
    LOGGER.debug("getProduct - converted product response: {}", productResponse);

    return productResponse;
  }

  @Override
  @Transactional
  public List<ProductResponse> addFavoriteProducts(List<Long> productIds) {
    UserAccount user = (UserAccount) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();

    LOGGER.debug("addFavoriteProducts - START - productIds: {}, userId: {}", productIds, user.getId());

    List<ProductResponse> addedProducts = new ArrayList<>();

    for (Long productId : productIds) {
      if (productFavoriteRepository.existsByUserAndProductId(user, productId)) {
        LOGGER.debug("addFavoriteProducts - product already favorited, skipping: {}", productId);
        continue;
      }

      ProductDto product = dummyJsonClient.getProductById(productId);
      LOGGER.debug("addFavoriteProducts - retrieved product: {}", product);

      ProductFavorite favorite = ProductFavorite.builder()
          .user(user)
          .productId(productId)
          .build();

      productFavoriteRepository.save(favorite);
      LOGGER.debug("addFavoriteProducts - saved favorite for productId: {}", productId);

      ProductResponse productResponse = conversionService.convert(product, ProductResponse.class);
      addedProducts.add(productResponse);
    }

    LOGGER.debug("addFavoriteProducts - added {} products", addedProducts.size());

    return addedProducts;
  }
}
