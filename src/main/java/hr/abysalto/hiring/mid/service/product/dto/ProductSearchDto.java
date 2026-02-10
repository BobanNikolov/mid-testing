package hr.abysalto.hiring.mid.service.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchDto {
  private List<ProductDto> products;
  private Long total;
  private Long skip;
  private Long limit;
}
