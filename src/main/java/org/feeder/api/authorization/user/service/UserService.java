package org.feeder.api.authorization.user.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.feeder.api.authorization.user.UserMapper;
import org.feeder.api.authorization.user.UserRepository;
import org.feeder.api.authorization.user.entity.User;
import org.feeder.api.authorization.user.vo.UserRequestVO;
import org.feeder.api.authorization.user.vo.UserResponseVO;
import org.feeder.api.core.mapper.BaseMapper;
import org.feeder.api.core.service.BaseCrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService
    extends BaseCrudService<User, UserRequestVO, UserResponseVO> implements UserDetailsService {

  private final UserRepository repository;

  private final UserMapper mapper;

  @Override
  protected BaseMapper<User, UserRequestVO, UserResponseVO> getMapper() {
    return mapper;
  }

  @Override
  protected JpaRepository<User, UUID> getRepository() {
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
