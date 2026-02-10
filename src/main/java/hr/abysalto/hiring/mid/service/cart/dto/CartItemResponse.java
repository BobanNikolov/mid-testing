package hr.abysalto.hiring.mid.service.cart.dto;

import hr.abysalto.hiring.mid.service.product.dto.ProductResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Cart item information")
public class CartItemResponse {

  @Schema(description = "Cart item ID", example = "1")
  private Long id;

  @Schema(description = "Product ID", example = "5")
  private Long productId;

  @Schema(description = "Product details")
  private ProductResponse product;

  @Schema(description = "Quantity", example = "2")
  private Integer quantity;
}