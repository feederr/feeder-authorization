package org.feeder.api.authorization.user.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.feeder.api.authorization.role.entity.Role;
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
  @Size(max = 30)
  @Column(name = "us_name")
  private String username;

  @NotEmpty
  @Email
  @Size(max = 64)
  @Column(name = "us_email")
  private String email;

  @NotEmpty
  @Size(max = 64)
  @Column(name = "us_first_name")
  private String firstName;

  @NotEmpty
  @Size(max = 64)
  @Column(name = "us_last_name")
  private String lastName;

  @NotEmpty
  @Column(name = "us_pass")
  private String password;

  @Column(name = "us_enabled")
  private boolean enabled = true;

  @Column(name = "us_acc_non_expired")
  private boolean accountNonExpired = true;

  @Column(name = "us_acc_non_locked")
  private boolean accountNonLocked = true;

  @Column(name = "us_cred_non_expired")
  private boolean credentialsNonExpired = true;

  @NotNull
  @ManyToOne(cascade = {
      CascadeType.PERSIST,
      CascadeType.REFRESH,
      CascadeType.MERGE
  })
  @JoinColumn(name = "ro_id")
  private Role role;

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

    if (!role.getAuthorities().isEmpty()) {
      result = role.getAuthorities().stream()
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    return result;
  }
}
