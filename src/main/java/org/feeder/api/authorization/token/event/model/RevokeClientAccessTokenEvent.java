package org.feeder.api.authorization.token.event.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RevokeClientAccessTokenEvent extends ApplicationEvent {

  private static final long serialVersionUID = -1354994607804893662L;

  private final String clientId;

  public RevokeClientAccessTokenEvent(Object source, String clientId) {
    super(source);
    this.clientId = clientId;
  }
}
