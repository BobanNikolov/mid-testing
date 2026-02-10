package hr.abysalto.hiring.mid.service.cart.converter;

import hr.abysalto.hiring.mid.service.cart.dto.CartItemDto;
import hr.abysalto.hiring.mid.service.cart.dto.CartItemResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CartItemDtoToResponseConverter implements Converter<CartItemDto, CartItemResponse> {
  @Override
  public CartItemResponse convert(CartItemDto source) {
    if (source == null) {
      return null;
    }
    CartItemResponse target = new CartItemResponse();
    target.setId(source.getId());
    target.setProductId(source.getProductId());
    target.setQuantity(source.getQuantity());
    return target;
  }
}
