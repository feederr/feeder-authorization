package org.feeder.api.authorization.client.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.feeder.api.authorization.validation.ValidPassword;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientRequestVO extends ClientBaseVO {

  @ValidPassword
  private String clientSecret;
}
