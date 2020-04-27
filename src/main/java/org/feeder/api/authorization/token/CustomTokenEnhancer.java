package org.feeder.api.authorization.token;

import java.util.HashMap;
import java.util.Map;
import org.feeder.api.authorization.token.AccessTokenHelper.AccessTokenType;
import org.feeder.api.authorization.user.entity.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {

  @Override
  public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken,
      final OAuth2Authentication authentication) {

    final DefaultOAuth2AccessToken defaultAccessToken = (DefaultOAuth2AccessToken) accessToken;

    if (authentication.isClientOnly()) {
      defaultAccessToken.setAdditionalInformation(getAdditionalInfo());
    } else {
      if (authentication.getPrincipal() instanceof User) {
        final User user = (User) authentication.getPrincipal();
        defaultAccessToken.setAdditionalInformation(getAdditionalInfo(user));
      }
    }

    return super.enhance(accessToken, authentication);
  }

  private Map<String, Object> getAdditionalInfo() {
    return new HashMap<>() {{
      put(AccessTokenHelper.TOKEN_TYPE_KEY, AccessTokenType.CLIENT);
    }};
  }

  private Map<String, Object> getAdditionalInfo(User user) {
    return new HashMap<>() {{
      put(AccessTokenHelper.TOKEN_TYPE_KEY, AccessTokenType.USER);
      put(AccessTokenHelper.USER_ID_KEY, user.getId());
      put(AccessTokenHelper.USER_NAME_KEY, user.getUsername());
    }};
  }
}
