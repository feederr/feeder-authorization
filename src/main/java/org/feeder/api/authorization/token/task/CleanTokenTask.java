package org.feeder.api.authorization.token.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feeder.api.authorization.token.AccessTokenRepository;
import org.feeder.api.authorization.token.RefreshTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CleanTokenTask {

  private final AccessTokenRepository accessTokenRepository;

  private final RefreshTokenRepository refreshTokenRepository;

  @Transactional
  @Scheduled(cron = "${feeder.scheduling.task.clean-access-token:0 */15 * * * *}")
  public void cleanAccessToken() {

    log.debug("Clean access token task has been started");

    accessTokenRepository.deleteExpiredTokens();

    log.debug("Clean access token task has been finished");
  }

  @Transactional
  @Scheduled(cron = "${feeder.scheduling.task.clean-refresh-token:0 0 0 ? * *}")
  public void cleanRefreshToken() {

    log.debug("Clean refresh token task has been started");

    refreshTokenRepository.deleteExpiredTokens();

    log.debug("Clean refresh token task has been finished");
  }
}
