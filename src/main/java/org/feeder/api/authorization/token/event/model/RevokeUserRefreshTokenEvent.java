package org.feeder.api.authorization.token.event.model;


import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RevokeUserRefreshTokenEvent extends ApplicationEvent {

  private static final long serialVersionUID = -5469205163109963920L;

  private final UUID userId;

  public RevokeUserRefreshTokenEvent(Object source, UUID userId) {
    super(source);
    this.userId = userId;
  }
}
