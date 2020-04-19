package org.feeder.api.authorization;

import org.feeder.api.core.annotation.FeederService;
import org.springframework.boot.SpringApplication;

@FeederService
public class AuthorizationApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthorizationApplication.class, args);
  }
}
