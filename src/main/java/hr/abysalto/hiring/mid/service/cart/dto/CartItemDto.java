package hr.abysalto.hiring.mid.service.cart.dto;

import hr.abysalto.hiring.mid.service.product.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

  private Long id;
  private Long productId;
  private ProductDto product;
  private Integer quantity;
}