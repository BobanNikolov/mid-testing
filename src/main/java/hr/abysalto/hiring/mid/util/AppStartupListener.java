package hr.abysalto.hiring.mid.util;

import hr.abysalto.hiring.mid.service.user.RoleService;
import hr.abysalto.hiring.mid.service.user.UserAccountService;
import hr.abysalto.hiring.mid.service.user.dto.UserAccountCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppStartupListener {

  private final UserAccountService userAccountService;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;

  @EventListener(ApplicationReadyEvent.class)
  public void onApplicationReady() {
    LOGGER.debug("onApplicationReady - START");

    if (isEmpty(userAccountService.getAll())) {

      UserAccountCommand userAccountCommand = UserAccountCommand.builder()
          .firstName("Admin")
          .lastName("User")
          .username("admin")
          .displayName("Admin User")
          .password(passwordEncoder.encode("admin"))
          .roles(roleService.getRoles())
          .build();
      userAccountService.saveInitialUser(userAccountCommand);
    }

    LOGGER.debug("onApplicationReady - END");
  }

}

