package hr.abysalto.hiring.mid.service.product.conversion;

import hr.abysalto.hiring.mid.service.product.dto.ProductDto;
import hr.abysalto.hiring.mid.service.product.dto.ProductResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoToResponseConverter implements Converter<ProductDto, ProductResponse> {
  @Override
  public ProductResponse convert(ProductDto source) {
    if (source == null) {
      return null;
    }
    ProductResponse target = new ProductResponse();
    target.setId(source.getId());
    target.setTitle(source.getTitle());
    target.setDescription(source.getDescription());
    target.setPrice(source.getPrice());
    return target;
  }
}
