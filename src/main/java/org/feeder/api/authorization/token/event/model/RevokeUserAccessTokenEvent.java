package org.feeder.api.authorization.token.event.model;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RevokeUserAccessTokenEvent extends ApplicationEvent {

  private static final long serialVersionUID = -1354994607804893662L;

  private final UUID userId;

  public RevokeUserAccessTokenEvent(Object source, UUID userId) {
    super(source);
    this.userId = userId;
  }
}
