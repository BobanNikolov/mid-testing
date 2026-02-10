package hr.abysalto.hiring.mid.service.user.merger;

import hr.abysalto.hiring.mid.configuration.SpringOverrideFieldsMapperConfig;
import hr.abysalto.hiring.mid.model.UserAccount;
import hr.abysalto.hiring.mid.service.user.dto.UserAccountCommand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = SpringOverrideFieldsMapperConfig.class)
public interface UserAccountCommandToEntityMerger {
  UserAccount merge(@MappingTarget UserAccount userAccount,
                    UserAccountCommand userAccountCommand);
}
