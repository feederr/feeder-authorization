package org.feeder.api.authorization.user.vo;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserBaseVO {

  @NotEmpty
  private String username;

  private boolean enabled = true;

  private boolean accountNonExpired;

  private boolean accountNonLocked;

  private boolean credentialsNonExpired;

  @NotEmpty
  private Set<@NotBlank String> authorities;
}
