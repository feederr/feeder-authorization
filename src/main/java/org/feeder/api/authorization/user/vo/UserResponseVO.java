package org.feeder.api.authorization.user.vo;

import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.feeder.api.authorization.role.vo.RoleResponseVO;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserResponseVO extends UserBaseVO {

  private UUID id;

  private RoleResponseVO role;
}
