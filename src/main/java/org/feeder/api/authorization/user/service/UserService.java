package org.feeder.api.authorization.user.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.feeder.api.authorization.role.service.RoleService;
import org.feeder.api.authorization.user.UserMapper;
import org.feeder.api.authorization.user.UserRepository;
import org.feeder.api.authorization.user.entity.User;
import org.feeder.api.authorization.user.vo.UserRequestVO;
import org.feeder.api.authorization.user.vo.UserResponseVO;
import org.feeder.api.core.mapper.BaseMapper;
import org.feeder.api.core.search.JpaSpecificationRepository;
import org.feeder.api.core.service.BaseCrudService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService
    extends BaseCrudService<User, UserRequestVO, UserResponseVO> implements UserDetailsService {

  private final RoleService roleService;

  private final UserRepository repository;

  private final UserMapper mapper;

  @Override
  protected User createEntity(UserRequestVO vo, UUID id, Object... args) {

    User entity = mapper.toEntity(vo, id, roleService.getByRoleName(vo.getRole()));

    entity.setNew(true);

    return repository.save(entity);
  }

  @Override
  protected User updateEntity(User entity, UserRequestVO vo, Object... args) {

    mapper.updateEntity(entity, vo, roleService.getByRoleName(vo.getRole()));

    return repository.save(entity);
  }

  @Override
  protected BaseMapper<User, UserRequestVO, UserResponseVO> getMapper() {
    return mapper;
  }

  @Override
  protected JpaSpecificationRepository<User, UUID> getRepository() {
    return repository;
  }

  @Override
  protected Class<User> getEntityClass() {
    return User.class;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repository.findByUsernameIgnoreCase(username)
        .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
  }
}
