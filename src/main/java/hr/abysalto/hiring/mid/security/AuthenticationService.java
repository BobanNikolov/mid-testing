package hr.abysalto.hiring.mid.security;

import hr.abysalto.hiring.mid.security.dto.SignInRequest;
import hr.abysalto.hiring.mid.security.dto.SignUpRequest;

public interface AuthenticationService {
  String signUp(SignUpRequest request);

  String signIn(SignInRequest request);
}
