package org.feeder.api.authorization.user.vo;

import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserRequestVO extends UserBaseVO {

  @NotEmpty
  private String password;
}
