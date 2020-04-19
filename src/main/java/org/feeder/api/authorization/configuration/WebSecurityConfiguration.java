package org.feeder.api.authorization.configuration;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// CHECKSTYLE:OFF
@Configuration
@EnableWebSecurity
// NOTE: temporary solution to place filter before Authorization Server Filter and Resource Service Filter
@Order(HIGHEST_PRECEDENCE)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Value("${feeder.authorization.bcrypt-strength:12}")
  private int bCryptStrength;

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
  protected void configure(HttpSecurity http) {

    http.csrf().disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.NEVER);
  }
}
// CHECKSTYLE:ON
