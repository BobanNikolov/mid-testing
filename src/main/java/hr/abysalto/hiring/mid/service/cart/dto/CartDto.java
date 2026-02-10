package hr.abysalto.hiring.mid.service.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
  private Long id;
  private List<CartItemDto> items;
  private Integer totalItems;
  private Double totalPrice;
}