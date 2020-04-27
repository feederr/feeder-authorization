package org.feeder.api.authorization.configuration;

import lombok.extern.slf4j.Slf4j;
import org.feeder.api.authorization.token.CustomAccessTokenConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Slf4j
@Configuration
public class AccessTokenConverterConfiguration {

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
}
