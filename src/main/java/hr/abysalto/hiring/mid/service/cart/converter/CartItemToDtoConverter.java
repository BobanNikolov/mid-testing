package hr.abysalto.hiring.mid.service.cart.converter;

import hr.abysalto.hiring.mid.model.CartItem;
import hr.abysalto.hiring.mid.service.cart.dto.CartItemDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CartItemToDtoConverter implements Converter<CartItem, CartItemDto> {
  @Override
  public CartItemDto convert(CartItem source) {
    if (source == null) {
      return null;
    }
    CartItemDto target = new CartItemDto();
    target.setId(source.getId());
    target.setProductId(source.getProductId());
    target.setQuantity(source.getQuantity());
    return target;
  }
}
