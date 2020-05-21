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
@Table(name = "oauth_refresh_token")
public class RefreshToken extends BaseEntity<UUID> {

  @Id
  @Column(name = "ort_id")
  private UUID id;

  @Column(name = "us_name")
  private String username;

  @Column(name = "us_id")
  private UUID userId;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "ort_token_type")
  private TokenType tokenType;

  @EqualsAndHashCode.Exclude
  @Column(name = "ort_expiration", updatable = false)
  private LocalDateTime expiration;
}
