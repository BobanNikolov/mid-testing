package hr.abysalto.hiring.mid.service.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Cart information")
public class CartResponse {

  @Schema(description = "Cart ID", example = "1")
  private Long id;

  @Schema(description = "Cart items")
  private List<CartItemDto> items;

  @Schema(description = "Total number of items", example = "5")
  private Integer totalItems;

  @Schema(description = "Total price", example = "299.99")
  private Double totalPrice;
}