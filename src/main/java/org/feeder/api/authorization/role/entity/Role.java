package org.feeder.api.authorization.role.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.feeder.api.core.converter.SetToStringConverter;
import org.feeder.api.core.domain.BaseEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "role")
@Data
public class Role extends BaseEntity<UUID> {

  @Id
  @Column(name = "ro_id")
  private UUID id;

  @NotBlank
  @Size(max = 30)
  @Column(name = "ro_name", nullable = false)
  private String name;

  @NotEmpty
  @Column(name = "ro_authorities")
  @Convert(converter = SetToStringConverter.class)
  private Set<@NotBlank String> authorities;

  @EqualsAndHashCode.Exclude
  @CreatedDate
  @Column(name = "ro_created", updatable = false)
  private LocalDateTime created;

  @EqualsAndHashCode.Exclude
  @LastModifiedDate
  @Column(name = "ro_modified")
  private LocalDateTime modified;
}
