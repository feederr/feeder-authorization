package org.feeder.api.authorization.user;

import static org.modelmapper.convention.MatchingStrategies.STRICT;
import java.util.UUID;
import org.feeder.api.authorization.role.RoleMapper;
import org.feeder.api.authorization.role.entity.Role;
import org.feeder.api.authorization.user.entity.User;
import org.feeder.api.authorization.user.vo.UserRequestVO;
import org.feeder.api.authorization.user.vo.UserResponseVO;
import org.feeder.api.core.mapper.BaseMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements BaseMapper<User, UserRequestVO, UserResponseVO> {

  private final ModelMapper mapper = new ModelMapper();

  private final PasswordEncoder encoder;

  private final RoleMapper roleMapper;

  // NOTE: Lazy is used as temporary solution to overcome dependency cycle
  public UserMapper(@Lazy PasswordEncoder encoder, RoleMapper roleMapper) {

    mapper.getConfiguration().setMatchingStrategy(STRICT);

    mapper.createTypeMap(UserRequestVO.class, User.class)
        .addMappings(m -> {
          m.skip(User::setPassword);
          m.skip(User::setRole);
        });

    mapper.createTypeMap(User.class, UserResponseVO.class)
        .addMappings(m -> m.skip(UserResponseVO::setRole));

    this.encoder = encoder;
    this.roleMapper = roleMapper;
  }

  @Override
  public UserResponseVO toResponseVO(User entity, Object... args) {
    UserResponseVO vo = mapper.map(entity, UserResponseVO.class);
    vo.setRole(roleMapper.toResponseVO(entity.getRole()));
    return vo;
  }

  @Override
  public User toEntity(UserRequestVO vo, Object... args) {

    UUID id = get(args, 0, UUID.class);
    Role role = get(args, 1, Role.class);

    User entity = mapper.map(vo, User.class);

    entity.setId(id);
    entity.setPassword(encoder.encode(vo.getPassword()));
    entity.setRole(role);

    return entity;
  }

  @Override
  public void updateEntity(User entity, UserRequestVO vo, Object... args) {

    Role role = get(args, 0, Role.class);

    mapper.map(vo, entity);

    entity.setRole(role);
    entity.setPassword(encoder.encode(vo.getPassword()));
  }
}
