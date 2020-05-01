package org.feeder.api.authorization.configuration;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.feeder.api.authorization.user.entity.User;
import org.feeder.api.core.util.TokenHelper;
import org.feeder.api.core.util.TokenHelper.TokenType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Slf4j
@Configuration
public class TokenConverterConfiguration {

  @Configuration
  @Profile("local")
  public static class MacAccessTokenConverterConfiguration {

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {

      log.debug("Using MAC based access token signature");

      JwtAccessTokenConverter converter = new CustomAccessTokenConverter();
      converter.setSigningKey("local_signing_key");

      return converter;
    }

  }

  @Configuration
  @Profile("!local")
  public static class RsaAccessTokenConverterConfiguration {

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {

      log.debug("Using RSA based access token signature");

      JwtAccessTokenConverter converter = new CustomAccessTokenConverter();
//      converter.setKeyPair(); TODO: add KeyPair generation

      return converter;
    }
  }

  public static class CustomAccessTokenConverter extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken,
        final OAuth2Authentication authentication) {

      final DefaultOAuth2AccessToken defaultAccessToken = (DefaultOAuth2AccessToken) accessToken;

      if (authentication.isClientOnly()) {
        final String clientId = (String) authentication.getPrincipal();
        defaultAccessToken.setAdditionalInformation(getAdditionalInfo(clientId));
      } else {
        if (authentication.getPrincipal() instanceof User) {
          final User user = (User) authentication.getPrincipal();
          defaultAccessToken.setAdditionalInformation(getAdditionalInfo(user));
        }
      }

      return super.enhance(accessToken, authentication);
    }

    private Map<String, Object> getAdditionalInfo(String clientId) {
      return Map.of(
          TokenHelper.TOKEN_TYPE_KEY, TokenType.CLIENT,
          TokenHelper.CLIENT_ID_KEY, clientId
      );
    }

    private Map<String, Object> getAdditionalInfo(User user) {
      return Map.of(
          TokenHelper.TOKEN_TYPE_KEY, TokenType.USER,
          TokenHelper.USER_ID_KEY, user.getId(),
          TokenHelper.USER_NAME_KEY, user.getUsername(),
          TokenHelper.USER_ROLE_KEY, user.getRole().getName()
      );
    }
  }
}
