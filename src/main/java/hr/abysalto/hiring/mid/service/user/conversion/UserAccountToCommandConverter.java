package hr.abysalto.hiring.mid.service.user.conversion;

import hr.abysalto.hiring.mid.model.UserAccount;
import hr.abysalto.hiring.mid.service.user.dto.UserAccountCommand;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserAccountToCommandConverter implements Converter<UserAccount, UserAccountCommand> {

  @Override
  public UserAccountCommand convert(UserAccount source) {
    if (source == null) {
      return null;
    }

    return UserAccountCommand.builder()
        .id(source.getId())
        .username(source.getUsername())
        .password(source.getPassword())
        .firstName(source.getFirstName())
        .lastName(source.getLastName())
        .displayName(source.getDisplayName())
        .build();
  }
}
