package hr.abysalto.hiring.mid.service.user.impl;

import hr.abysalto.hiring.mid.model.UserAccount;
import hr.abysalto.hiring.mid.repository.UserAccountRepository;
import hr.abysalto.hiring.mid.service.user.UserAccountService;
import hr.abysalto.hiring.mid.service.user.dto.UserAccountCommand;
import hr.abysalto.hiring.mid.service.user.dto.UserAccountDto;
import hr.abysalto.hiring.mid.service.user.merger.UserAccountCommandToEntityMerger;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserAccountServiceImpl implements UserAccountService {

  private final UserAccountRepository repository;
  private final ConversionService conversionService;
  private final UserAccountCommandToEntityMerger userAccountCommandToEntityMerger;

  @Override
  public UserAccountDto getUserAccountByUsername(String username) {
    LOGGER.debug("getUserAccountByUsername - START - username: {}", username);
    Optional<UserAccount> userAccountOptional = repository.findByUsername(username);
    if (userAccountOptional.isEmpty()) {
      LOGGER.debug("getUserAccountByUsername - END - user is not found, returning null");
      return null;
    }
    UserAccount userAccount = userAccountOptional.get();
    UserAccountDto result = conversionService.convert(userAccount, UserAccountDto.class);
    LOGGER.debug("getUserAccountByUsername - END - result: {}", result);
    return result;
  }

  @Override
  public UserAccountDto save(UserAccountCommand userAccountCommand) {
    LOGGER.debug("save - START - userAccountCommand: {}", userAccountCommand);

    UserAccount userAccountToSave = userAccountCommandToEntityMerger.merge(new UserAccount(),
        userAccountCommand);
    LOGGER.debug("save - merged user account: {}", userAccountToSave);

    UserAccount savedUserAccount = repository.saveAndFlush(userAccountToSave);
    LOGGER.debug("save - updated user account: {}", savedUserAccount);

    UserAccountDto result = conversionService.convert(savedUserAccount, UserAccountDto.class);
    LOGGER.debug("save - converted user account: {}", result);

    return result;
  }

  @Override
  public UserAccountDto update(UserAccountCommand userAccountCommand) {
    LOGGER.debug("update - START - userAccountCommand: {}", userAccountCommand);
    Optional<UserAccount> userAccountOptional = repository.findById(userAccountCommand.getId());
    if (userAccountOptional.isEmpty()) {
      LOGGER.debug("update - END - user is not found, returning null");
      return null;
    }
    UserAccount userAccountToUpdate = userAccountCommandToEntityMerger.merge(
        userAccountOptional.get(), userAccountCommand);
    LOGGER.debug("update - merged user account: {}", userAccountToUpdate);

    UserAccount updatedUserAccount = repository.saveAndFlush(userAccountToUpdate);
    LOGGER.debug("update - updated user account: {}", updatedUserAccount);

    UserAccountDto result = conversionService.convert(updatedUserAccount, UserAccountDto.class);
    LOGGER.debug("update - converted user account: {}", result);

    return result;
  }

  public UserDetailsService userDetailsService() {
    return username -> repository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  @Override
  public void saveInitialUser(UserAccountCommand userAccountCommand) {
    LOGGER.debug("saveInitialUser - START - userAccountCommand: {}", userAccountCommand);

    UserAccount userAccountToSave = userAccountCommandToEntityMerger.merge(new UserAccount(),
        userAccountCommand);
    LOGGER.debug("saveInitialUser - merged user account: {}", userAccountToSave);

    UserAccount savedUserAccount = repository.saveAndFlush(userAccountToSave);
    LOGGER.debug("saveInitialUser - updated user account: {}", savedUserAccount);

    UserAccountDto result = conversionService.convert(savedUserAccount, UserAccountDto.class);
    LOGGER.debug("saveInitialUser - converted user account: {}", result);
  }

  @Override
  public List<UserAccountDto> getAll() {
    LOGGER.debug("getAll - START");
    List<UserAccountDto> result = repository.findAll()
        .stream()
        .map(it -> conversionService.convert(it, UserAccountDto.class))
        .toList();
    LOGGER.debug("getAll - END - result: {}", result);
    return result;
  }

  @Override
  public UserAccountDto getCurrentUser() {
    LOGGER.debug("getCurrentUser - START");

    UserAccount user = (UserAccount) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();

    LOGGER.debug("getCurrentUser - retrieved user from context: {}", user);

    UserAccountDto userAccountDto = conversionService.convert(user, UserAccountDto.class);

    LOGGER.debug("getCurrentUser - converted user: {}", userAccountDto);

    return userAccountDto;
  }

}
