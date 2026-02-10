package hr.abysalto.hiring.mid.security;

import hr.abysalto.hiring.mid.service.user.dto.UserAccountDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
  String extractUserName(String token);
  String generateToken(UserAccountDto userDetails);
  boolean isTokenValid(String token, UserDetails userDetails);
}
