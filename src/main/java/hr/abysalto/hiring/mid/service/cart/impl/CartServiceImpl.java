package hr.abysalto.hiring.mid.service.cart.impl;

import hr.abysalto.hiring.mid.feign.DummyJsonClient;
import hr.abysalto.hiring.mid.model.Cart;
import hr.abysalto.hiring.mid.model.CartItem;
import hr.abysalto.hiring.mid.model.UserAccount;
import hr.abysalto.hiring.mid.repository.CartItemRepository;
import hr.abysalto.hiring.mid.repository.CartRepository;
import hr.abysalto.hiring.mid.service.cart.CartService;
import hr.abysalto.hiring.mid.service.cart.dto.CartDto;
import hr.abysalto.hiring.mid.service.cart.dto.CartItemDto;
import hr.abysalto.hiring.mid.service.product.dto.ProductDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final DummyJsonClient dummyJsonClient;

  @Override
  @Transactional
  public CartDto addProductToCart(Long productId, Integer quantity) {
    UserAccount user = (UserAccount) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();

    LOGGER.debug("addProductToCart - START - productId: {}, quantity: {}, userId: {}",
        productId, quantity, user.getId());

    Cart cart = cartRepository.findByUser(user)
        .orElseGet(() -> {
          LOGGER.debug("addProductToCart - creating new cart for user: {}", user.getId());
          Cart newCart = Cart.builder()
              .user(user)
              .items(new ArrayList<>())
              .build();
          return cartRepository.save(newCart);
        });

    LOGGER.debug("addProductToCart - cart: {}", cart);

    ProductDto product = dummyJsonClient.getProductById(productId);
    LOGGER.debug("addProductToCart - verified product exists: {}", product);

    Optional<CartItem> existingItem = cartItemRepository.findByCartAndProductId(cart, productId);

    if (existingItem.isPresent()) {
      LOGGER.debug("addProductToCart - updating existing cart item");
      CartItem item = existingItem.get();
      item.setQuantity(item.getQuantity() + quantity);
      cartItemRepository.save(item);
    } else {
      LOGGER.debug("addProductToCart - creating new cart item");
      CartItem newItem = CartItem.builder()
          .cart(cart)
          .productId(productId)
          .quantity(quantity)
          .build();
      cartItemRepository.save(newItem);
      cart.getItems().add(newItem);
    }

    CartDto cartDto = buildCartDto(cart);
    LOGGER.debug("addProductToCart - END - cartDto: {}", cartDto);

    return cartDto;
  }

  @Override
  @Transactional
  public CartDto removeProductFromCart(Long productId) {
    UserAccount user = (UserAccount) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();

    LOGGER.debug("removeProductFromCart - START - productId: {}, userId: {}",
        productId, user.getId());

    Cart cart = cartRepository.findByUser(user)
        .orElseThrow(() -> {
          LOGGER.error("removeProductFromCart - cart not found for user: {}", user.getId());
          return new IllegalStateException("Cart not found");
        });

    LOGGER.debug("removeProductFromCart - found cart: {}", cart);

    CartItem itemToRemove = cartItemRepository.findByCartAndProductId(cart, productId)
        .orElseThrow(() -> {
          LOGGER.error("removeProductFromCart - cart item not found for productId: {}", productId);
          return new IllegalStateException("Product not found in cart");
        });

    LOGGER.debug("removeProductFromCart - found cart item to remove: {}", itemToRemove);

    cart.getItems().remove(itemToRemove);
    cartItemRepository.delete(itemToRemove);
    cartRepository.save(cart);

    LOGGER.debug("removeProductFromCart - deleted cart item for productId: {}", productId);

    cart = cartRepository.findById(cart.getId())
        .orElseThrow(() -> new IllegalStateException("Cart not found"));

    CartDto cartDto = buildCartDto(cart);
    LOGGER.debug("removeProductFromCart - END - cartDto: {}", cartDto);

    return cartDto;
  }

  @Override
  public CartDto getCurrentCart() {
    UserAccount user = (UserAccount) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();

    LOGGER.debug("getCurrentCart - START - userId: {}", user.getId());

    Cart cart = cartRepository.findByUser(user)
        .orElseGet(() -> {
          LOGGER.debug("getCurrentCart - no cart found, returning empty cart");
          return Cart.builder()
              .items(new ArrayList<>())
              .build();
        });

    LOGGER.debug("getCurrentCart - found cart: {}", cart);

    CartDto cartDto = buildCartDto(cart);
    LOGGER.debug("getCurrentCart - END - cartDto: {}", cartDto);

    return cartDto;
  }

  private CartDto buildCartDto(Cart cart) {
    LOGGER.debug("buildCartDto - START - cart: {}", cart);

    List<CartItemDto> items = cart.getItems().stream()
        .map(item -> {
          try {
            ProductDto productDto = dummyJsonClient.getProductById(item.getProductId());

            return CartItemDto.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .product(productDto)
                .quantity(item.getQuantity())
                .build();
          } catch (Exception e) {
            LOGGER.error("buildCartDto - error fetching product: {}", item.getProductId(), e);
            return null;
          }
        })
        .filter(Objects::nonNull)
        .toList();

    Integer totalItems = items.stream()
        .mapToInt(CartItemDto::getQuantity)
        .sum();

    Double totalPrice = items.stream()
        .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
        .sum();

    CartDto cartDto = CartDto.builder()
        .id(cart.getId())
        .items(items)
        .totalItems(totalItems)
        .totalPrice(totalPrice)
        .build();

    LOGGER.debug("buildCartDto - END - cartDto: {}", cartDto);

    return cartDto;
  }
}