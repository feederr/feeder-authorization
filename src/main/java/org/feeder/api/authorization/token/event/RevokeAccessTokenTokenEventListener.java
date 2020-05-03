package org.feeder.api.authorization.token.event;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feeder.api.authorization.token.AccessTokenRepository;
import org.feeder.api.authorization.token.event.model.RevokeClientAccessTokenEvent;
import org.feeder.api.authorization.token.event.model.RevokeUserAccessTokenEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RevokeAccessTokenTokenEventListener {

  private final AccessTokenRepository accessTokenRepository;

  @Async
  @EventListener
  @Transactional(propagation = Propagation.REQUIRED)
  public void listenRevokeClientAccessTokenEvent(RevokeClientAccessTokenEvent event) {

    if (Objects.isNull(event.getClientId())) {
      throw new IllegalArgumentException("Client id must not be null");
    }

    log.debug("Revoking Access token by client id = {}", event.getClientId());

    accessTokenRepository.deleteByClientId(event.getClientId());
  }

  @Async
  @EventListener
  @Transactional(propagation = Propagation.REQUIRED)
  public void listenRevokeUserAccessTokenEvent(RevokeUserAccessTokenEvent event) {

    if (Objects.isNull(event.getUserId())) {
      throw new IllegalArgumentException("User id must not be null");
    }

    log.debug("Revoking Access token by user id = {}", event.getUserId());

    accessTokenRepository.deleteByUserId(event.getUserId());
  }
}
