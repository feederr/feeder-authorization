package org.feeder.api.authorization.user.vo;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserBaseVO {

  @NotEmpty
  private String username;

  private boolean enabled = true;

  private boolean accountNonExpired = true;

  private boolean accountNonLocked = true;

  private boolean credentialsNonExpired = true;
}
