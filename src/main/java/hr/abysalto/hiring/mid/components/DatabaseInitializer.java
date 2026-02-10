package hr.abysalto.hiring.mid.components;

import hr.abysalto.hiring.mid.model.*;
import hr.abysalto.hiring.mid.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
	private final RoleRepository roleRepository;
	private final UserAccountRepository userRepository;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final ProductFavoriteRepository favoriteRepository;
	private final PasswordEncoder passwordEncoder;


	@Override
	public void run(String... args) throws Exception {
//		initRoles();
//		initUsers();
	}

	private void initRoles() {
		if (roleRepository.count() > 0) return;

		Role userRole = Role.builder()
				.name("ROLE_USER")
				.build();

		Role adminRole = Role.builder()
				.name("ROLE_ADMIN")
				.build();

		roleRepository.saveAll(List.of(userRole, adminRole));
	}

	private void initUsers() {
		if (userRepository.count() > 0) return;

		Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow();

		UserAccount user = UserAccount.builder()
				.username("john")
				.password(passwordEncoder.encode("password"))
				.firstName("John")
				.lastName("Doe")
				.displayName("John Doe")
				.roles(Set.of(userRole))
				.build();

		user = userRepository.save(user);

		// Cart
		Cart cart = Cart.builder()
				.user(user)
				.build();

		cart = cartRepository.save(cart);
		user.setCart(cart);

		// Cart items (DummyJSON product IDs)
		CartItem item1 = CartItem.builder()
				.cart(cart)
				.productId(1L)
				.quantity(2)
				.build();

		CartItem item2 = CartItem.builder()
				.cart(cart)
				.productId(5L)
				.quantity(1)
				.build();

		cartItemRepository.saveAll(List.of(item1, item2));

		// Favorites
		ProductFavorite favorite1 = ProductFavorite.builder()
				.user(user)
				.productId(3L)
				.build();

		ProductFavorite favorite2 = ProductFavorite.builder()
				.user(user)
				.productId(7L)
				.build();

		favoriteRepository.saveAll(List.of(favorite1, favorite2));
	}
}
