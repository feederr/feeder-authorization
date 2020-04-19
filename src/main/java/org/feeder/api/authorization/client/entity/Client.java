package org.feeder.api.authorization.client.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.feeder.api.core.converter.SetToStringConverter;
import org.feeder.api.core.domain.BaseEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "oauth_client")
public class Client extends BaseEntity<UUID> implements ClientDetails {

  private static final long serialVersionUID = 6741552823498224205L;

  @Id
  @Column(name = "oc_id")
  private UUID id;

  @NotBlank
  @Size(max = 100)
  @Column(name = "oc_client_id", nullable = false)
  private String clientId;

  @NotBlank
  @Size(max = 60)
  @Column(name = "oc_secret", nullable = false)
  private String clientSecret;

  @NotEmpty
  @Column(name = "oc_resource_ids", nullable = false)
  @Convert(converter = SetToStringConverter.class)
  private Set<@NotBlank String> resourceIds;

  @NotEmpty
  @Column(name = "oc_scope", nullable = false)
  @Convert(converter = SetToStringConverter.class)
  private Set<@NotBlank String> scope;

  @NotEmpty
  @Column(name = "oc_authorized_grant_types", nullable = false)
  @Convert(converter = SetToStringConverter.class)
  private Set<@NotBlank String> authorizedGrantTypes;

  @Size(max = 200)
  @Column(name = "oc_registered_redirect_uris")
  @Convert(converter = SetToStringConverter.class)
  private Set<@NotBlank String> registeredRedirectUri;

  @NotEmpty
  @Column(name = "oc_authorities")
  @Convert(converter = SetToStringConverter.class)
  private Set<@NotBlank String> authorities;

  @Column(name = "oc_auto_approve")
  @Convert(converter = SetToStringConverter.class)
  private Set<@NotBlank String> autoApprove;

  @NotNull
  @Column(name = "oc_access_token_expiration", nullable = false)
  private Integer accessTokenExpiration;

  @Column(name = "oc_refresh_token_expiration")
  private Integer refreshTokenExpiration;

  @EqualsAndHashCode.Exclude
  @CreatedDate
  @Column(name = "oc_created", updatable = false)
  private LocalDateTime created;

  @EqualsAndHashCode.Exclude
  @LastModifiedDate
  @Column(name = "oc_modified")
  private LocalDateTime modified;

  @Override
  public boolean isSecretRequired() {
    return true;
  }

  @Override
  public boolean isScoped() {
    return !getScope().isEmpty();
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {

    Set<GrantedAuthority> result = new LinkedHashSet<>();

    if (Objects.nonNull(authorities) && !authorities.isEmpty()) {
      result = authorities.stream()
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    return result;
  }

  @Override
  public Integer getAccessTokenValiditySeconds() {
    return getAccessTokenExpiration();
  }

  @Override
  public Integer getRefreshTokenValiditySeconds() {
    return getRefreshTokenExpiration();
  }

  @Override
  public boolean isAutoApprove(String scope) {

    if (Objects.nonNull(autoApprove)) {
      return autoApprove.contains(scope);
    }

    return false;
  }

  @Override
  public Map<String, Object> getAdditionalInformation() {
    return new HashMap<>();
  }
}

