package org.feeder.api.authorization.role;

import java.util.Optional;
import java.util.UUID;
import org.feeder.api.authorization.role.entity.Role;
import org.feeder.api.core.search.JpaSpecificationRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaSpecificationRepository<Role, UUID> {

  Optional<Role> findByNameIgnoreCase(String name);
}
