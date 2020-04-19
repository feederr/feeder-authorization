package org.feeder.api.authorization.client.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientRequestVO extends ClientBaseVO {

  @NotBlank
  @Size(max = 60)
  private String clientSecret;
}
