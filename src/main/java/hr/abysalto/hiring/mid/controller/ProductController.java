package hr.abysalto.hiring.mid.controller;

import hr.abysalto.hiring.mid.service.product.ProductService;
import hr.abysalto.hiring.mid.service.product.dto.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Product management APIs using DummyJSON")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping
  @Operation(
      summary = "Get all products",
      description = "Retrieves a paginated list of products from DummyJSON API"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved products",
          content = @Content(schema = @Schema(implementation = Page.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public Page<ProductResponse> getProducts(
      @Parameter(description = "Pagination parameters (page number, size, sort)")
      Pageable pageable) {

    LOGGER.debug("getProducts - START - pageable: {}", pageable);

    List<ProductResponse> productResponses = productService.getProducts(pageable);
    LOGGER.debug("getProducts - retrieved product responses: {}", productResponses.size());

    long total = productResponses.size();

    Page<ProductResponse> result = new PageImpl<>(
        productResponses,
        pageable,
        total
    );

    LOGGER.debug("getProducts - returning page: {}", result);
    return result;
  }

  @GetMapping("/{id}")
  @Operation(
      summary = "Get product by ID",
      description = "Retrieves a single product by its unique identifier from DummyJSON API"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved product",
          content = @Content(schema = @Schema(implementation = ProductResponse.class))),
      @ApiResponse(responseCode = "404", description = "Product not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ProductResponse getProductById(
      @Parameter(description = "Product ID", example = "1")
      @PathVariable Long id) {

    LOGGER.debug("getProductById - START - id: {}", id);

    ProductResponse result = productService.getProduct(id);

    LOGGER.debug("getProductById - returning product: {}", result);
    return result;
  }

  @PostMapping("/favorites")
  @Operation(
      summary = "Add products to favorites",
      description = "Adds multiple products to the authenticated user's favorites list"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Products added to favorites",
          content = @Content(schema = @Schema(implementation = ProductResponse.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
  public List<ProductResponse> addFavoriteProducts(
      @Parameter(description = "List of product IDs to add to favorites")
      @RequestBody List<Long> productIds) {

    LOGGER.debug("addFavoriteProducts - START - productIds: {}", productIds);

    List<ProductResponse> result = productService.addFavoriteProducts(productIds);

    LOGGER.debug("addFavoriteProducts - added {} products", result.size());
    return result;
  }
}