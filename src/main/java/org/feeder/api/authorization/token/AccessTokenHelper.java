package org.feeder.api.authorization.token;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessTokenHelper {

  public static final String TOKEN_TYPE_KEY = "_t";

  public static final String USER_ID_KEY = "user_id";

  public static final String USER_NAME_KEY = "username";

  public enum AccessTokenType {
    CLIENT, USER
  }
}
