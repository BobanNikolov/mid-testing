package hr.abysalto.hiring.mid.service.user;

import hr.abysalto.hiring.mid.service.user.dto.UserAccountCommand;
import hr.abysalto.hiring.mid.service.user.dto.UserAccountDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserAccountService {

  UserAccountDto getUserAccountByUsername(String username);

  UserAccountDto save(UserAccountCommand userAccountCommand);

  UserAccountDto update(UserAccountCommand userAccountCommand);

  UserDetailsService userDetailsService();

  void saveInitialUser(UserAccountCommand userAccountCommand);

  List<UserAccountDto> getAll();

  UserAccountDto getCurrentUser();
}
