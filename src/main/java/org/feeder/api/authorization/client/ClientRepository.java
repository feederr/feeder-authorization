package org.feeder.api.authorization.client;

import java.util.Optional;
import java.util.UUID;
import org.feeder.api.authorization.client.entity.Client;
import org.feeder.api.core.search.JpaSpecificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaSpecificationRepository<Client, UUID> {

  Optional<Client> findByClientIdIgnoreCase(String clientId);
}
