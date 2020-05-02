package org.feeder.api.authorization.logout;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.feeder.api.authorization.token.event.model.RevokeClientAccessTokenEvent;
import org.feeder.api.authorization.token.event.model.RevokeUserAccessTokenEvent;
import org.feeder.api.authorization.token.event.model.RevokeUserRefreshTokenEvent;
import org.feeder.api.core.util.TokenHelper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {

  private final ApplicationEventPublisher publisher;

  public void logoutUser() {

    Optional<UUID> userIdOpt = TokenHelper.extractUserId();

    if (userIdOpt.isPresent() && TokenHelper.isUserToken()) {

      RevokeUserAccessTokenEvent revokeAccessTokenEvent
          = new RevokeUserAccessTokenEvent(this, userIdOpt.get());

      RevokeUserRefreshTokenEvent revokeRefreshTokenEvent =
          new RevokeUserRefreshTokenEvent(this, userIdOpt.get());

      publisher.publishEvent(revokeAccessTokenEvent);
      publisher.publishEvent(revokeRefreshTokenEvent);

    } else {
      throw new AccessDeniedException("Access denied");
    }
  }

  public void logoutClient() {

    Optional<String> clientIdOpt = TokenHelper.extractClientId();

    if (clientIdOpt.isPresent() && TokenHelper.isClientToken()) {

      RevokeClientAccessTokenEvent revokeAccessTokenEvent
          = new RevokeClientAccessTokenEvent(this, clientIdOpt.get());

      publisher.publishEvent(revokeAccessTokenEvent);

    } else {
      throw new AccessDeniedException("Access denied");
    }
  }
}
