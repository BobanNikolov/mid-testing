package hr.abysalto.hiring.mid.security.impl;

import hr.abysalto.hiring.mid.repository.UserAccountRepository;
import hr.abysalto.hiring.mid.security.AuthenticationService;
import hr.abysalto.hiring.mid.security.JwtService;
import hr.abysalto.hiring.mid.security.dto.SignInRequest;
import hr.abysalto.hiring.mid.security.dto.SignUpRequest;
import hr.abysalto.hiring.mid.service.user.UserAccountService;
import hr.abysalto.hiring.mid.service.user.dto.UserAccountCommand;
import hr.abysalto.hiring.mid.service.user.dto.UserAccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserAccountRepository userRepository;
  private final UserAccountService userAccountService;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final ConversionService conversionService;

  @Override
  public String signUp(SignUpRequest request) {
    LOGGER.debug("signUp - START - request: {}", request);
    var user = conversionService.convert(request, UserAccountCommand.class);
    if (user == null) {
      LOGGER.debug("signUp - request is bad, returning null");
      return null;
    }
    var savedUser = userAccountService.save(user);
    LOGGER.debug("signUp - END - savedUser: {}", savedUser);
    return jwtService.generateToken(savedUser);
  }


  @Override
  public String signIn(SignInRequest request) {
    LOGGER.debug("signIn - START - request: {}", request);
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    } catch (BadCredentialsException e) {
      throw new IllegalArgumentException();
    }
    LOGGER.debug("signIn - auth passed successfully");
    UserAccountDto user = userAccountService.getUserAccountByUsername(request.getUsername());
    LOGGER.debug("signIn - END - fetched user: {}", user);
    return jwtService.generateToken(user);
  }

}
