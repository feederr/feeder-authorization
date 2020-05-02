package org.feeder.api.authorization.token;

import java.util.UUID;
import org.feeder.api.authorization.token.entity.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, UUID> {

  void deleteByRefreshTokenId(UUID refreshTokenId);

  void deleteByClientId(String clientId);

  void deleteByUserId(UUID userId);

  @Modifying
  @Query("DELETE FROM AccessToken token WHERE token.expiration < CURRENT_TIMESTAMP")
  void deleteExpiredTokens();
}
