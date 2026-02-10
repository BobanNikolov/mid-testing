package hr.abysalto.hiring.mid.security.conversion;

import hr.abysalto.hiring.mid.security.dto.SignUpRequest;
import hr.abysalto.hiring.mid.service.user.dto.UserAccountCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpRequestToUserAccountCommandConverter implements
    Converter<SignUpRequest, UserAccountCommand> {

  private final PasswordEncoder passwordEncoder;

  @Override
  public UserAccountCommand convert(SignUpRequest source) {
    if (source == null) {
      return null;
    }
    return UserAccountCommand
        .builder()
        .firstName(source.getFirstName())
        .lastName(source.getLastName())
        .username(source.getUsername())
        .displayName(source.getFirstName() + " " + source.getLastName())
        .password(passwordEncoder.encode(source.getPassword()))
        .build();
  }
}
