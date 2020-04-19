package org.feeder.api.authorization.client.vo;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientBaseVO {

  @NotBlank
  @Size(max = 100)
  private String clientId;

  @NotEmpty
  private Set<@NotBlank String> resourceIds;

  @NotEmpty
  private Set<@NotBlank String> scope;

  @NotEmpty
  private Set<@NotBlank String> authorizedGrantTypes;

  @Size(max = 200)
  private Set<@NotBlank String> registeredRedirectUri;

  @NotEmpty
  private Set<@NotBlank String> authorities;

  private Set<@NotBlank String> autoApprove;

  @NotNull
  private Integer accessTokenExpiration;

  private Integer refreshTokenExpiration;
}
