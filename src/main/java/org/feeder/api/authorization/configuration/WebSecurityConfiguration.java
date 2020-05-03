package org.feeder.api.authorization.configuration;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.feeder.api.authorization.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// CHECKSTYLE:OFF
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Value("${feeder.security.bcrypt-strength:12}")
  private int bCryptStrength;

  private final UserService userService;

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder(bCryptStrength);
  }

  @Bean
  @Override
  @SneakyThrows
  protected AuthenticationManager authenticationManager() {
    return super.authenticationManager();
  }

  @Override
  @SneakyThrows
  protected void configure(AuthenticationManagerBuilder builder) {
    builder.userDetailsService(userService);
  }
}
// CHECKSTYLE:ON
