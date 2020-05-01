package org.feeder.api.authorization.token.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.feeder.api.core.domain.BaseEntity;
import org.feeder.api.core.util.TokenHelper.TokenType;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "oauth_access_token")
public class AccessToken extends BaseEntity<UUID> {

  @Id
  @Column(name = "oat_id")
  private UUID id;

  @Column(name = "oat_username")
  private String username;

  @Column(name = "us_id")
  private UUID userId;

  @Column(name = "oat_user_role")
  private String role;

  @Column(name = "oc_client_id")
  private String clientId;

  @Column(name = "oat_refresh_token_id")
  private UUID refreshTokenId;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "oat_token_type")
  private TokenType tokenType;

  @EqualsAndHashCode.Exclude
  @Column(name = "oat_expiration", updatable = false)
  private LocalDateTime expiration;
}
