package org.feeder.api.authorization.user;

import java.util.Optional;
import java.util.UUID;
import org.feeder.api.authorization.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByUsernameIgnoreCase(String username);
}
