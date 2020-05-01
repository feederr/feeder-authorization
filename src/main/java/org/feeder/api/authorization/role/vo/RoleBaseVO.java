package org.feeder.api.authorization.role.vo;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class RoleBaseVO {

  @NotBlank
  @Size(max = 30)
  private String name;

  @NotEmpty
  private Set<@NotBlank String> authorities;
}
