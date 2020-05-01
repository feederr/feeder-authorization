package org.feeder.api.authorization.token.mapper;

import static java.util.UUID.fromString;
import static org.feeder.api.core.util.TokenHelper.CLIENT_ID_KEY;
import static org.feeder.api.core.util.TokenHelper.JTI_KEY;
import static org.feeder.api.core.util.TokenHelper.TOKEN_TYPE_KEY;
import static org.feeder.api.core.util.TokenHelper.TokenType.valueOf;
import static org.feeder.api.core.util.TokenHelper.USER_ID_KEY;
import static org.feeder.api.core.util.TokenHelper.USER_NAME_KEY;
import static org.feeder.api.core.util.TokenHelper.USER_ROLE_KEY;
import static org.feeder.api.core.util.TokenHelper.getClaim;
import static org.feeder.api.core.util.TokenHelper.getClaims;
import static org.feeder.api.core.util.UUIDUtils.optimizedUUIDFrom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import org.feeder.api.authorization.token.entity.AccessToken;
import org.feeder.api.core.util.TokenHelper.TokenType;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenMapper {

  public AccessToken map(OAuth2AccessToken oAuth2AccessToken) {

    AccessToken accessToken = new AccessToken();

    Optional<Map<String, Object>> properties = getClaims(oAuth2AccessToken.getValue());

    properties.ifPresent(map -> {

      accessToken.setId(optimizedUUIDFrom(fromString(getClaim(map, JTI_KEY))));
      accessToken.setTokenType(valueOf(getClaim(map, TOKEN_TYPE_KEY)));

      if (accessToken.getTokenType() == TokenType.USER) {
        accessToken.setUsername(getClaim(map, USER_NAME_KEY));
        // don't need to optimize cuz we optimized it during user creation
        accessToken.setUserId(fromString(getClaim(map, USER_ID_KEY)));
        accessToken.setRole(getClaim(map, USER_ROLE_KEY));
      }

      if (accessToken.getTokenType() == TokenType.CLIENT) {
        accessToken.setClientId(getClaim(map, CLIENT_ID_KEY));
      }

      accessToken.setExpiration(LocalDateTime.now().plusSeconds(oAuth2AccessToken.getExpiresIn()));

      accessToken.setNew(true);
    });

    return accessToken;
  }
}
