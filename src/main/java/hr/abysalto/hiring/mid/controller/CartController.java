package hr.abysalto.hiring.mid.controller;

import hr.abysalto.hiring.mid.service.cart.CartService;
import hr.abysalto.hiring.mid.service.cart.dto.AddToCartRequest;
import hr.abysalto.hiring.mid.service.cart.dto.CartDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart", description = "Shopping cart management APIs")
@Slf4j
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  @PostMapping("/items")
  @Operation(
      summary = "Add product to cart",
      description = "Adds a product to the authenticated user's shopping cart"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product added to cart",
          content = @Content(schema = @Schema(implementation = CartDto.class))),
      @ApiResponse(responseCode = "404", description = "Product not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
  public ResponseEntity<CartDto> addProductToCart(
      @Parameter(description = "Add to cart request")
      @RequestBody AddToCartRequest request) {

    LOGGER.debug("addProductToCart - START - request: {}", request);

    CartDto cart = cartService.addProductToCart(request.getProductId(), request.getQuantity());

    LOGGER.debug("addProductToCart - END - cart: {}", cart);
    return ResponseEntity.ok(cart);
  }

  @DeleteMapping("/items/{productId}")
  @Operation(
      summary = "Remove product from cart",
      description = "Removes a product from the authenticated user's shopping cart"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product removed from cart",
          content = @Content(schema = @Schema(implementation = CartDto.class))),
      @ApiResponse(responseCode = "404", description = "Cart or product not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
  public ResponseEntity<CartDto> removeProductFromCart(
      @Parameter(description = "Product ID", example = "1")
      @PathVariable Long productId) {

    LOGGER.debug("removeProductFromCart - START - productId: {}", productId);

    CartDto cart = cartService.removeProductFromCart(productId);

    LOGGER.debug("removeProductFromCart - END - cart: {}", cart);
    return ResponseEntity.ok(cart);
  }

  @GetMapping
  @Operation(
      summary = "Get current cart",
      description = "Retrieves the authenticated user's current shopping cart"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved cart",
          content = @Content(schema = @Schema(implementation = CartDto.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
  public ResponseEntity<CartDto> getCurrentCart() {

    LOGGER.debug("getCurrentCart - START");

    CartDto cart = cartService.getCurrentCart();

    LOGGER.debug("getCurrentCart - END - cart: {}", cart);
    return ResponseEntity.ok(cart);
  }
}