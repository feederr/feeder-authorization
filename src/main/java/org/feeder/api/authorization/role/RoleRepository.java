package org.feeder.api.authorization.role;

import java.util.Optional;
import java.util.UUID;
import org.feeder.api.authorization.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

  Optional<Role> findByNameIgnoreCase(String name);
}
