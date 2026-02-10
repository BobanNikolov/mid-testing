package hr.abysalto.hiring.mid.service.cart;

import hr.abysalto.hiring.mid.service.cart.dto.CartDto;

public interface CartService {

  CartDto addProductToCart(Long productId, Integer quantity);

  CartDto removeProductFromCart(Long productId);

  CartDto getCurrentCart();
}
