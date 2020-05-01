package org.feeder.api.authorization.role;

import static org.modelmapper.convention.MatchingStrategies.STRICT;
import java.util.UUID;
import org.feeder.api.authorization.role.entity.Role;
import org.feeder.api.authorization.role.vo.RoleRequestVO;
import org.feeder.api.authorization.role.vo.RoleResponseVO;
import org.feeder.api.core.mapper.BaseMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper implements BaseMapper<Role, RoleRequestVO, RoleResponseVO> {

  private final ModelMapper mapper = new ModelMapper();

  public RoleMapper() {
    mapper.getConfiguration().setMatchingStrategy(STRICT);
  }

  @Override
  public RoleResponseVO toResponseVO(Role entity, Object... args) {
    return mapper.map(entity, RoleResponseVO.class);
  }

  @Override
  public Role toEntity(RoleRequestVO vo, Object... args) {

    UUID id = get(args, 0, UUID.class);

    Role entity = mapper.map(vo, Role.class);
    entity.setId(id);

    return entity;
  }

  @Override
  public void updateEntity(Role entity, RoleRequestVO vo, Object... args) {
    mapper.map(vo, entity);
  }
}
