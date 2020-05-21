package org.feeder.api.authorization.user.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class UserBaseVO {

  @NotEmpty
  @Size(max = 30)
  private String username;

  @NotNull
  @Email
  @Size(max = 64)
  private String email;

  @NotEmpty
  @Size(max = 64)
  private String firstName;

  @NotEmpty
  @Size(max = 64)
  private String lastName;
}
