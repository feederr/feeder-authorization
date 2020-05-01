package org.feeder.api.authorization.token;

import java.util.List;
import java.util.UUID;
import org.feeder.api.authorization.token.entity.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, UUID> {

  List<AccessToken> findAllByUsername(String username);

  List<AccessToken> findAllByRole(String role);

  List<AccessToken> findAllByClientId(String clientId);

  List<AccessToken> findAllByRefreshTokenId(UUID refreshTokenId);

  void deleteByRefreshTokenId(UUID refreshTokenId);

  @Modifying
  @Query("DELETE FROM AccessToken token WHERE token.expiration < CURRENT_TIMESTAMP")
  void deleteExpiredTokens();
}
