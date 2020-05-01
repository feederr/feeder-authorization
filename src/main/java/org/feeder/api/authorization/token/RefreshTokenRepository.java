package org.feeder.api.authorization.token;

import java.util.List;
import java.util.UUID;
import org.feeder.api.authorization.token.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

  List<RefreshToken> findAllByUsername(String username);

  @Modifying
  @Query("DELETE FROM RefreshToken token where token.expiration < CURRENT_TIMESTAMP")
  void deleteExpiredTokens();
}
