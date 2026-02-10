package hr.abysalto.hiring.mid.configuration;

import hr.abysalto.hiring.mid.service.user.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties
@RequiredArgsConstructor
public class ResourceServerConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final UserAccountService userService;
  private final PasswordEncoder passwordEncoder;

  @Bean
  public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
    configureRequestSecurity(http);
    configurePermittedRequests(http);
    configureActuatorRequests(http);
    configureAuthenticatedRequests(http);
    return http.build();
  }

  private void configureRequestSecurity(final HttpSecurity http) throws Exception {
    http
        .cors(withDefaults())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
  }

  private void configureActuatorRequests(final HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(EndpointRequest.to("health")).permitAll()
            .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR"))
        .httpBasic(AbstractHttpConfigurer::disable);
  }

  private void configureAuthenticatedRequests(final HttpSecurity http) throws Exception {
    // allow API access when authenticated
    http
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**",
                "/v3/api-docs.yaml",
                "/webjars/**",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security", "/auth/**").permitAll()
            .anyRequest().authenticated())
        .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .formLogin(AbstractHttpConfigurer::disable);
  }

  private void configurePermittedRequests(final HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)

        // allow OPTIONS calls to API
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.OPTIONS)
            .permitAll())

        // permit register user
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.POST,
                "/v1/sites")
            .permitAll())

        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.GET,
                // ping
                "/ping")
            .permitAll())

        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.HEAD,
                // ping
                "/ping")
            .permitAll())

        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(
                // permit static recipeResources
                "/static/**")
            .permitAll());
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(List.of("*"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);
    configuration.setExposedHeaders(List.of(CONTENT_DISPOSITION));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User.withDefaultPasswordEncoder()
        .username("user")
        .password("password")
        .roles("USER")
        .build();
    UserDetails admin = User.withDefaultPasswordEncoder()
        .username("admin")
        .password("password")
        .roles("ADMIN", "USER", "ACTUATOR")
        .build();
    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userService.userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}
