package org.feeder.api.authorization.logout;

import static org.feeder.api.authorization.logout.LogoutController.LOGOUT_PATH;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(LOGOUT_PATH)
@RequiredArgsConstructor
public class LogoutController {

  protected static final String LOGOUT_PATH = "/logout";

  private static final String USER_PATH = "/user";

  private static final String CLIENT_PATH = "/client";

  private final LogoutService service;

  @PostMapping(USER_PATH)
  public ResponseEntity<?> logoutUser() {
    service.logoutUser();
    return ResponseEntity.ok()
        .build();
  }

  @PostMapping(CLIENT_PATH)
  public ResponseEntity<?> logoutClient() {
    service.logoutClient();
    return ResponseEntity.ok()
        .build();
  }
}
