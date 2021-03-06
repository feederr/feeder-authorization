package org.feeder.api.authorization.token;

import static java.util.UUID.fromString;
import static org.feeder.api.core.util.TokenHelper.JTI_KEY;
import static org.feeder.api.core.util.TokenHelper.getClaim;
import static org.feeder.api.core.util.TokenHelper.getClaims;
import static org.feeder.api.core.util.UUIDUtils.optimizedUUIDFrom;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.feeder.api.authorization.token.entity.AccessToken;
import org.feeder.api.authorization.token.entity.RefreshToken;
import org.feeder.api.authorization.token.mapper.AccessTokenMapper;
import org.feeder.api.authorization.token.mapper.RefreshTokenMapper;
import org.feeder.api.core.util.TokenHelper.TokenType;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CustomJwtTokenStore extends JwtTokenStore {

  private final AccessTokenRepository accessTokenRepository;

  private final RefreshTokenRepository refreshTokenRepository;

  private final AccessTokenMapper accessTokenMapper;

  private final RefreshTokenMapper refreshTokenMapper;

  public CustomJwtTokenStore(
      JwtAccessTokenConverter jwtTokenEnhancer,
      AccessTokenRepository accessTokenRepository,
      RefreshTokenRepository refreshTokenRepository,
      AccessTokenMapper accessTokenMapper,
      RefreshTokenMapper refreshTokenMapper) {
    super(jwtTokenEnhancer);
    this.accessTokenRepository = accessTokenRepository;
    this.refreshTokenRepository = refreshTokenRepository;
    this.accessTokenMapper = accessTokenMapper;
    this.refreshTokenMapper = refreshTokenMapper;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {

    AccessToken accessToken = accessTokenMapper.map(token);

    if (accessToken.getTokenType() != TokenType.CLIENT
        && Objects.nonNull(token.getRefreshToken())) {

      RefreshToken refreshToken = refreshTokenMapper.map(token.getRefreshToken());
      refreshTokenRepository.save(refreshToken);

      accessToken.setRefreshTokenId(refreshToken.getId());
    }

    accessTokenRepository.save(accessToken);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public OAuth2AccessToken readAccessToken(String tokenValue) {

    Optional<Map<String, Object>> claims = getClaims(tokenValue);

    claims.ifPresent(map -> {
      UUID accessTokenId = optimizedUUIDFrom(fromString(getClaim(map, JTI_KEY)));
      if (!accessTokenRepository.existsById(accessTokenId)) {
        throw new InvalidTokenException("Access token expired"); // TODO: implement custom exception
      }
    });

    return super.readAccessToken(tokenValue);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public OAuth2RefreshToken readRefreshToken(String tokenValue) {

    Optional<Map<String, Object>> claims = getClaims(tokenValue);

    claims.ifPresent(map -> {
      UUID refreshTokenId = optimizedUUIDFrom(fromString(getClaim(map, JTI_KEY)));
      if (!refreshTokenRepository.existsById(refreshTokenId)) {
        throw new InvalidTokenException(
            "Refresh token expired"); // TODO: implement custom exception
      }
    });

    return super.readRefreshToken(tokenValue);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void removeAccessToken(OAuth2AccessToken token) {
    AccessToken accessToken = accessTokenMapper.map(token);
    accessTokenRepository.deleteById(accessToken.getId());
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void removeRefreshToken(OAuth2RefreshToken token) {
    RefreshToken refreshToken = refreshTokenMapper.map(token);
    refreshTokenRepository.deleteById(refreshToken.getId());
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken token) {
    RefreshToken refreshToken = refreshTokenMapper.map(token);
    accessTokenRepository.deleteByRefreshTokenId(refreshToken.getId());
  }
}
