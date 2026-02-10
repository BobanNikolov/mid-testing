package hr.abysalto.hiring.mid.service.user.conversion;

import hr.abysalto.hiring.mid.model.Role;
import hr.abysalto.hiring.mid.model.UserAccount;
import hr.abysalto.hiring.mid.service.user.dto.UserAccountDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserAccountToDtoConverter implements Converter<UserAccount, UserAccountDto> {

  @Override
  public UserAccountDto convert(UserAccount source) {
    if (source == null) {
      return null;
    }

    return UserAccountDto.builder()
        .id(source.getId())
        .username(source.getUsername())
        .password(source.getPassword())
        .firstName(source.getFirstName())
        .lastName(source.getLastName())
        .displayName(source.getDisplayName())
        .roles(source.getRoles().stream()
            .map(Role::getAuthority)
            .toList())
        .build();
  }
}
