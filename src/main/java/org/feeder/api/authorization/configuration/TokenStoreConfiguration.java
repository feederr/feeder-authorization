package org.feeder.api.authorization.configuration;

import org.feeder.api.authorization.token.AccessTokenRepository;
import org.feeder.api.authorization.token.CustomJwtTokenStore;
import org.feeder.api.authorization.token.RefreshTokenRepository;
import org.feeder.api.authorization.token.mapper.AccessTokenMapper;
import org.feeder.api.authorization.token.mapper.RefreshTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class TokenStoreConfiguration {

  @Configuration
  @ConditionalOnProperty(
      value = "feeder.security.jdbc-token-store.enabled",
      havingValue = "true"
  )
  public static class CustomJwtTokenStoreConfiguration {

    @Autowired
    private JwtAccessTokenConverter tokenConverter;

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private AccessTokenMapper accessTokenMapper;

    @Autowired
    private RefreshTokenMapper refreshTokenMapper;

    @Bean
    public TokenStore tokenStore() {
      return new CustomJwtTokenStore(
          tokenConverter,
          accessTokenRepository,
          refreshTokenRepository,
          accessTokenMapper,
          refreshTokenMapper
      );
    }
  }

  @Configuration
  @ConditionalOnProperty(
      value = "feeder.security.jdbc-token-store.enabled",
      havingValue = "false",
      matchIfMissing = true
  )
  public static class DefaultJwtTokenStoreConfiguration {

    @Autowired
    private JwtAccessTokenConverter tokenConverter;

    @Bean
    public TokenStore tokenStore() {
      return new JwtTokenStore(tokenConverter);
    }
  }
}
