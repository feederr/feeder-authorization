package org.feeder.api.authorization.role.vo;

import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleResponseVO extends RoleBaseVO {

  private UUID id;
}
