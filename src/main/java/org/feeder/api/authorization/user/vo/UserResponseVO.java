package org.feeder.api.authorization.user.vo;

import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserResponseVO extends UserBaseVO {

  private UUID id;
}
