package hr.abysalto.hiring.mid.service.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to add product to cart")
public class AddToCartRequest {

  @Schema(description = "Product ID", example = "1")
  private Long productId;

  @Schema(description = "Quantity to add", example = "2")
  private Integer quantity;
}