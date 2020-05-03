package org.feeder.api.authorization.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.feeder.api.authorization.client.service.ClientService;
import org.feeder.api.authorization.user.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  private final AuthenticationManager authenticationManager;

  private final UserService userService;

  private final ClientService clientService;

  private final AccessTokenConverter accessTokenConverter;

  private final TokenStore tokenStore;

  @Override
  @SneakyThrows
  public void configure(ClientDetailsServiceConfigurer configurer) {
    configurer.withClientDetails(clientService);
  }

  @Override
  @SneakyThrows
  public void configure(AuthorizationServerEndpointsConfigurer configurer) {
    configurer.authenticationManager(authenticationManager)
        .tokenStore(tokenStore)
        .userDetailsService(userService)
        .accessTokenConverter(accessTokenConverter)
        .exceptionTranslator(new CustomExceptionTranslator());
  }

  @Override
  @SneakyThrows
  public void configure(AuthorizationServerSecurityConfigurer configurer) {
    configurer.tokenKeyAccess("permitAll()")
        .checkTokenAccess("permitAll()");
  }

  public static class CustomExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    @Override
    @SneakyThrows
    public ResponseEntity<OAuth2Exception> translate(Exception exception) {

      if (exception instanceof OAuth2Exception) {

        OAuth2Exception oAuth2Exception = (OAuth2Exception) exception;
        return ResponseEntity.status(oAuth2Exception.getHttpErrorCode())
            .contentType(MediaType.APPLICATION_JSON)
            .body(new CustomOAuth2Exception(oAuth2Exception.getMessage()));

      } else if (exception instanceof AuthenticationException) {

        AuthenticationException authenticationException = (AuthenticationException) exception;
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new CustomOAuth2Exception(authenticationException.getMessage()));
      }

      return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT)
          .contentType(MediaType.APPLICATION_JSON)
          .body(new CustomOAuth2Exception(exception.getMessage()));
    }

    @JsonSerialize(using = CustomOAuth2ExceptionSerializer.class)
    public static class CustomOAuth2Exception extends OAuth2Exception {

      private static final long serialVersionUID = -322958766181402455L;

      public CustomOAuth2Exception(String message) {
        super(message);
      }
    }

    public static class CustomOAuth2ExceptionSerializer extends StdSerializer<CustomOAuth2Exception> {

      private static final long serialVersionUID = -6536424010044954576L;

      private static final String STATUS_FIELD = "status";

      private static final String MESSAGE_FIELD = "message";

      private static final String ERRORS_FIELD = "errors";

      public CustomOAuth2ExceptionSerializer() {
        super(CustomOAuth2Exception.class);
      }

      @Override
      @SneakyThrows(IOException.class)
      public void serialize(
          CustomOAuth2Exception value,
          JsonGenerator generator,
          SerializerProvider provider) {

        generator.writeStartObject();

        generator.writeObjectField(STATUS_FIELD, HttpStatus.valueOf(value.getHttpErrorCode()));
        generator.writeStringField(MESSAGE_FIELD, value.getMessage());
        generator.writeObjectField(ERRORS_FIELD, List.of(value.getOAuth2ErrorCode()));

        if (value.getAdditionalInformation() != null) {
          for (Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
            String key = entry.getKey();
            String add = entry.getValue();
            generator.writeStringField(key, add);
          }
        }

        generator.writeEndObject();
      }
    }
  }
}
