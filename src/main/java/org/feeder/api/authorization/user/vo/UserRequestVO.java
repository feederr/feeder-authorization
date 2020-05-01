package org.feeder.api.authorization.user.vo;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.feeder.api.authorization.validation.ValidPassword;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserRequestVO extends UserBaseVO {

  @ValidPassword
  private String password;

  @NotBlank
  private String role;
}
