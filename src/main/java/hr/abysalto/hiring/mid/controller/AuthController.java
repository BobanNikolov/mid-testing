package hr.abysalto.hiring.mid.controller;

import hr.abysalto.hiring.mid.security.AuthenticationService;
import hr.abysalto.hiring.mid.security.dto.SignInRequest;
import hr.abysalto.hiring.mid.security.dto.SignInResponse;
import hr.abysalto.hiring.mid.security.dto.SignUpRequest;
import hr.abysalto.hiring.mid.security.dto.SignUpResponse;
import hr.abysalto.hiring.mid.service.user.UserAccountService;
import hr.abysalto.hiring.mid.service.user.dto.UserAccountDto;
import hr.abysalto.hiring.mid.service.user.dto.UserAccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication",
    description = "Authentication APIs to handle the service's security.")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
  private final UserAccountService userAccountService;
  private final AuthenticationService authenticationService;
  private final ConversionService conversionService;

  @Operation(summary = "Sign Up")
  @ApiResponse(responseCode = "200", description = "Returns a valid token.")
  @PostMapping("/signup")
  public ResponseEntity<SignUpResponse> signup(
      @RequestBody @Parameter(description = "Sign up request") SignUpRequest signUpRequest) {
    LOGGER.debug("signup - START - signUpRequest: {}", signUpRequest);
    UserAccountDto userAccount = userAccountService.getUserAccountByUsername(signUpRequest.getUsername());
    LOGGER.debug("signup - userAccount: {}", userAccount);
    if (userAccount != null) {
      LOGGER.debug("signup - END - userAccount exists");
      SignUpResponse response = SignUpResponse.builder()
          .message("Email already exists")
          .build();
      return ResponseEntity.badRequest().body(response);
    }
    String savedUser = authenticationService.signUp(signUpRequest);
    if (savedUser == null) {
      LOGGER.debug("signup - END - failed to sign up user");
      SignUpResponse response = SignUpResponse.builder()
          .message("Failed to sign up the user!")
          .build();
      return ResponseEntity.badRequest().body(response);
    }
    LOGGER.debug("signup - END - savedUser: {}", savedUser);
    SignUpResponse response = SignUpResponse.builder()
        .message("User registered successfully")
        .token(savedUser)
        .build();
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Sign In")
  @ApiResponse(responseCode = "200", description = "Returns a valid token.")
  @PostMapping("/signin")
  public ResponseEntity<SignInResponse> signin(
      @RequestBody @Parameter(description = "Sign in request") SignInRequest signInRequest) {
    LOGGER.debug("signin - START - signInRequest: {}", signInRequest);
    String token = authenticationService.signIn(signInRequest);
    SignInResponse response = SignInResponse.builder()
        .token(token)
        .build();
    LOGGER.debug("signin - END - response: {}", response);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Get Current User",
      description = "Retrieve information about the currently logged-in user")
  @ApiResponse(responseCode = "200", description = "Returns information about the currently logged-in user.",
      content = @Content(schema = @Schema(implementation = UserAccountResponse.class)))
  @ApiResponse(responseCode = "401", description = "Unauthorized")
  @GetMapping("/me")
  public ResponseEntity<UserAccountResponse> getCurrentUser() {
    LOGGER.debug("getCurrentUser - START");

    UserAccountDto userAccountDto = userAccountService.getCurrentUser();
    LOGGER.debug("getCurrentUser - userAccountDto: {}", userAccountDto);

    UserAccountResponse response = conversionService.convert(userAccountDto, UserAccountResponse.class);
    LOGGER.debug("getCurrentUser - response: {}", response);

    LOGGER.debug("getCurrentUser - END");
    return ResponseEntity.ok(response);
  }
}
