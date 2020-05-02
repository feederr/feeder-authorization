package org.feeder.api.authorization.token.mapper;

import static java.time.ZoneId.of;
import static java.util.UUID.fromString;
import static org.feeder.api.core.util.TokenHelper.JTI_KEY;
import static org.feeder.api.core.util.TokenHelper.TOKEN_TYPE_KEY;
import static org.feeder.api.core.util.TokenHelper.TokenType.valueOf;
import static org.feeder.api.core.util.TokenHelper.USER_ID_KEY;
import static org.feeder.api.core.util.TokenHelper.USER_NAME_KEY;
import static org.feeder.api.core.util.TokenHelper.getClaim;
import static org.feeder.api.core.util.TokenHelper.getClaims;
import static org.feeder.api.core.util.UUIDUtils.optimizedUUIDFrom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.feeder.api.authorization.token.entity.RefreshToken;
import org.feeder.api.core.util.TokenHelper.TokenType;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {

  public RefreshToken map(OAuth2RefreshToken oAuth2RefreshToken) {

    RefreshToken refreshToken = new RefreshToken();

    Optional<Map<String, Object>> properties = getClaims(oAuth2RefreshToken.getValue());

    properties.ifPresent(map -> {

      refreshToken.setId(optimizedUUIDFrom(fromString(getClaim(map, JTI_KEY))));
      refreshToken.setTokenType(valueOf(getClaim(map, TOKEN_TYPE_KEY)));

      if (refreshToken.getTokenType() == TokenType.USER) {
        refreshToken.setUsername(getClaim(map, USER_NAME_KEY));
        // don't need to optimized cuz we optimized it during user creation
        refreshToken.setUserId(fromString(getClaim(map, USER_ID_KEY)));
      }

      if (oAuth2RefreshToken instanceof ExpiringOAuth2RefreshToken) {

        Date exp = ((ExpiringOAuth2RefreshToken) oAuth2RefreshToken).getExpiration();

        if (Objects.isNull(exp)) {
          return;
        }

        LocalDateTime expLocalDateTime = exp.toInstant()
            .atZone(of(ZoneOffset.UTC.getId()))
            .toLocalDateTime();

        refreshToken.setExpiration(expLocalDateTime);
      }
    });

    return refreshToken;
  }
}
