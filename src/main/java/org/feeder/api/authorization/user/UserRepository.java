package org.feeder.api.authorization.user;

import java.util.Optional;
import java.util.UUID;
import org.feeder.api.authorization.user.entity.User;
import org.feeder.api.core.search.JpaSpecificationRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaSpecificationRepository<User, UUID> {

  Optional<User> findByUsernameIgnoreCase(String username);
}
