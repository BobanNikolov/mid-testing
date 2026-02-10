package hr.abysalto.hiring.mid.service.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Product information retrieved from DummyJSON API")
public class ProductResponse {

  @Schema(description = "Unique identifier of the product", example = "1")
  private Long id;

  @Schema(description = "Product title/name", example = "iPhone 15 Pro")
  private String title;

  @Schema(description = "Detailed description of the product",
      example = "The latest iPhone with advanced features and improved camera")
  private String description;

  @Schema(description = "Product price in USD", example = "999.99")
  private Double price;
}