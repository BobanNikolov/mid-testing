package hr.abysalto.hiring.mid.security.impl;

import hr.abysalto.hiring.mid.security.JwtService;
import hr.abysalto.hiring.mid.security.properties.TokenProperties;
import hr.abysalto.hiring.mid.service.user.dto.UserAccountDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

  private final TokenProperties tokenProperties;

  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public String generateToken(UserAccountDto userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String userName = extractUserName(token);
    return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
    final Claims claims = extractAllClaims(token);
    return claimsResolvers.apply(claims);
  }

  private String generateToken(Map<String, Object> extraClaims, UserAccountDto userDetails) {
    extraClaims.put("id", userDetails.getId());
    extraClaims.put("username", userDetails.getUsername());
    extraClaims.put("firstName", userDetails.getFirstName());
    extraClaims.put("lastName", userDetails.getLastName());
    extraClaims.put("displayName", userDetails.getDisplayName());
    extraClaims.put("roles", userDetails.getRoles());

    return Jwts
        .builder()
        .claims(extraClaims)
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + tokenProperties.getExpirationMs()))
        .signWith(getSigningKey(), Jwts.SIG.HS256)
        .compact();
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(tokenProperties.getSecret());
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
