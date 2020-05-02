package org.feeder.api.authorization.token.event;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feeder.api.authorization.token.RefreshTokenRepository;
import org.feeder.api.authorization.token.event.model.RevokeUserRefreshTokenEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RevokeRefreshTokenEventListener {

  private final RefreshTokenRepository refreshTokenRepository;

  @Async
  @EventListener
  @Transactional(propagation = Propagation.REQUIRED)
  public void listenRevokeUserRefreshTokenEvent(RevokeUserRefreshTokenEvent event) {

    if (Objects.isNull(event.getUserId())) {
      throw new IllegalArgumentException("User id must not be null");
    }

    log.debug("Revoking Refresh token by user id = {}", event.getUserId());

    refreshTokenRepository.deleteByUserId(event.getUserId());
  }
}
