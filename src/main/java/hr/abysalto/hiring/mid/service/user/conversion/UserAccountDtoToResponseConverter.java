package hr.abysalto.hiring.mid.service.user.conversion;

import hr.abysalto.hiring.mid.service.user.dto.UserAccountDto;
import hr.abysalto.hiring.mid.service.user.dto.UserAccountResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserAccountDtoToResponseConverter implements Converter<UserAccountDto, UserAccountResponse> {

  @Override
  public UserAccountResponse convert(UserAccountDto source) {
    if (source == null) {
      return null;
    }

    Set<String> roles = source.getRoles() != null
        ? new HashSet<>(source.getRoles())
        : new HashSet<>();

    return UserAccountResponse.builder()
        .id(source.getId())
        .username(source.getUsername())
        .firstName(source.getFirstName())
        .lastName(source.getLastName())
        .displayName(source.getDisplayName())
        .roles(roles)
        .build();
  }
}