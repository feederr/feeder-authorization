package org.feeder.api.authorization.user;

import static org.modelmapper.convention.MatchingStrategies.STRICT;

import java.util.UUID;
import org.feeder.api.authorization.user.entity.User;
import org.feeder.api.authorization.user.vo.UserRequestVO;
import org.feeder.api.authorization.user.vo.UserResponseVO;
import org.feeder.api.core.mapper.BaseMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements BaseMapper<User, UserRequestVO, UserResponseVO> {

  private ModelMapper mapper = new ModelMapper();

  private PasswordEncoder encoder;

  // NOTE: Lazy is used as temporary solution to overcome dependency cycle
  public UserMapper(@Lazy PasswordEncoder encoder) {

    mapper.getConfiguration().setMatchingStrategy(STRICT);

    mapper.addMappings(new PropertyMap<UserRequestVO, User>() {
      @Override
      protected void configure() {
        skip().setPassword(null);
      }
    });

    this.encoder = encoder;
  }

  @Override
  public UserResponseVO toResponseVO(User entity, Object... args) {
    return mapper.map(entity, UserResponseVO.class);
  }

  @Override
  public User toEntity(UserRequestVO vo, Object... args) {

    UUID id = get(args, 0, UUID.class);

    User entity = mapper.map(vo, User.class);

    entity.setId(id);
    entity.setPassword(encoder.encode(vo.getPassword()));

    return entity;
  }

  @Override
  public void updateEntity(User entity, UserRequestVO vo, Object... args) {
    mapper.map(vo, entity);
    entity.setPassword(encoder.encode(vo.getPassword()));
  }
}
