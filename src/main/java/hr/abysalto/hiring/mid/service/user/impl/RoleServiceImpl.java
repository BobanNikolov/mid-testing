package hr.abysalto.hiring.mid.service.user.impl;

import hr.abysalto.hiring.mid.model.Role;
import hr.abysalto.hiring.mid.repository.RoleRepository;
import hr.abysalto.hiring.mid.service.user.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;

  @Override
  public Set<Role> getRoles() {
    return new HashSet<>(roleRepository.findAll());
  }
}
