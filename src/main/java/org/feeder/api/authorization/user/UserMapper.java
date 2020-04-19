package org.feeder.api.authorization.user;

import static org.modelmapper.convention.MatchingStrategies.STRICT;

import java.util.UUID;
import org.feeder.api.authorization.user.entity.User;
import org.feeder.api.authorization.user.vo.UserRequestVO;
import org.feeder.api.authorization.user.vo.UserResponseVO;
import org.feeder.api.core.mapper.BaseMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements BaseMapper<User, UserRequestVO, UserResponseVO> {

  private ModelMapper mapper = new ModelMapper();

  public UserMapper() {
    mapper.getConfiguration().setMatchingStrategy(STRICT);
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

    return entity;
  }

  @Override
  public void updateEntity(User entity, UserRequestVO vo, Object... args) {
    mapper.map(vo, entity);
  }
}
