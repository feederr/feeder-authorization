package org.feeder.api.authorization.configuration;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.feeder.api.authorization.client.service.ClientService;
import org.feeder.api.authorization.user.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  private final AuthenticationManager authenticationManager;

  private final UserService userService;

  private final ClientService clientService;

  @Override
  @SneakyThrows
  public void configure(ClientDetailsServiceConfigurer configurer) {
    configurer.withClientDetails(clientService);
  }

  @Override
  @SneakyThrows
  public void configure(AuthorizationServerEndpointsConfigurer configurer) {
    configurer.authenticationManager(authenticationManager)
        .userDetailsService(userService);
//        .tokenEnhancer() TODO: add in future
//        .exceptionTranslator() TODO: add in future
  }

  @Override
  @SneakyThrows
  public void configure(AuthorizationServerSecurityConfigurer configurer) {
    configurer.tokenKeyAccess("permitAll()")
        .checkTokenAccess("permitAll()");
  }
}