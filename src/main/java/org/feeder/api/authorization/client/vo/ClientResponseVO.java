package org.feeder.api.authorization.client.vo;

import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientResponseVO extends ClientBaseVO {

  private UUID id;
}
