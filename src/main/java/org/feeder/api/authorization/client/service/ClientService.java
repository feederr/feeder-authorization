package org.feeder.api.authorization.client.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.feeder.api.authorization.client.ClientMapper;
import org.feeder.api.authorization.client.ClientRepository;
import org.feeder.api.authorization.client.entity.Client;
import org.feeder.api.authorization.client.vo.ClientRequestVO;
import org.feeder.api.authorization.client.vo.ClientResponseVO;
import org.feeder.api.core.mapper.BaseMapper;
import org.feeder.api.core.service.BaseCrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService extends
    BaseCrudService<Client, ClientRequestVO, ClientResponseVO> implements ClientDetailsService {

  private final ClientRepository repository;

  private final ClientMapper mapper;

  @Override
  protected BaseMapper<Client, ClientRequestVO, ClientResponseVO> getMapper() {
    return mapper;
  }

  @Override
  protected JpaRepository<Client, UUID> getRepository() {
    return repository;
  }

  @Override
  protected Client createEntity(ClientRequestVO vo, UUID id, Object... args) {

    Client entity = mapper.toEntity(vo, id);

    entity.setNew(true);

    return repository.save(entity);
  }

  @Override
  protected Client updateEntity(Client entity, ClientRequestVO vo, Object... args) {

    mapper.updateEntity(entity, vo);

    return repository.save(entity);
  }

  @Override
  protected Class<Client> getEntityClass() {
    return Client.class;
  }

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws NoSuchClientException {
    return repository.findByClientIdIgnoreCase(clientId)
        .orElseThrow(() -> new NoSuchClientException("Client not found " + clientId));
  }
}