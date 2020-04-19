package org.feeder.api.authorization.user.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashSet;
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
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.feeder.api.core.converter.SetToStringConverter;
import org.feeder.api.core.domain.BaseEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "user")
public class User extends BaseEntity<UUID> implements UserDetails {

  @Id
  @Column(name = "us_id")
  private UUID id;

  @NotEmpty
  @Column(name = "us_name")
  private String username;

  @NotEmpty
  @Column(name = "us_pass")
  private String password;

  @Column(name = "us_enabled")
  private boolean enabled;

  @Column(name = "us_acc_non_expired")
  private boolean accountNonExpired;

  @Column(name = "us_acc_non_locked")
  private boolean accountNonLocked;

  @Column(name = "us_cred_non_expired")
  private boolean credentialsNonExpired;

  @NotEmpty
  @Column(name = "us_authorities")
  @Convert(converter = SetToStringConverter.class)
  private Set<@NotBlank String> authorities;

  @EqualsAndHashCode.Exclude
  @CreatedDate
  @Column(name = "us_created", updatable = false)
  private LocalDateTime created;

  @EqualsAndHashCode.Exclude
  @LastModifiedDate
  @Column(name = "us_modified")
  private LocalDateTime modified;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    Set<GrantedAuthority> result = new LinkedHashSet<>();

    if (!authorities.isEmpty()) {
      result = authorities.stream()
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    return result;
  }
}
