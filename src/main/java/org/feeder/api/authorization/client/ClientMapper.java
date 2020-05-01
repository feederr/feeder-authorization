package org.feeder.api.authorization.client;

import static org.modelmapper.convention.MatchingStrategies.STRICT;
import java.util.UUID;
import org.feeder.api.authorization.client.entity.Client;
import org.feeder.api.authorization.client.vo.ClientRequestVO;
import org.feeder.api.authorization.client.vo.ClientResponseVO;
import org.feeder.api.core.mapper.BaseMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper implements BaseMapper<Client, ClientRequestVO, ClientResponseVO> {

  private ModelMapper mapper = new ModelMapper();

  private PasswordEncoder encoder;

  public ClientMapper(@Lazy PasswordEncoder encoder) {

    mapper.getConfiguration().setMatchingStrategy(STRICT);

    mapper.createTypeMap(ClientRequestVO.class, Client.class)
        .addMappings(m -> m.skip(Client::setClientSecret));

    this.encoder = encoder;
  }

  @Override
  public ClientResponseVO toResponseVO(Client entity, Object... args) {
    return mapper.map(entity, ClientResponseVO.class);
  }

  @Override
  public Client toEntity(ClientRequestVO vo, Object... args) {

    UUID id = get(args, 0, UUID.class);

    Client entity = mapper.map(vo, Client.class);

    entity.setId(id);
    entity.setClientSecret(encoder.encode(vo.getClientSecret()));

    return entity;
  }

  @Override
  public void updateEntity(Client entity, ClientRequestVO vo, Object... args) {
    mapper.map(vo, entity);
    entity.setClientSecret(encoder.encode(vo.getClientSecret()));
  }
}
