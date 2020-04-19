package org.feeder.api.authorization.client;

import static org.feeder.api.authorization.client.ClientController.CLIENT_PATH;

import lombok.RequiredArgsConstructor;
import org.feeder.api.authorization.client.entity.Client;
import org.feeder.api.authorization.client.service.ClientService;
import org.feeder.api.authorization.client.vo.ClientRequestVO;
import org.feeder.api.authorization.client.vo.ClientResponseVO;
import org.feeder.api.core.controller.BaseCrudController;
import org.feeder.api.core.service.BaseCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(CLIENT_PATH)
public class ClientController extends
    BaseCrudController<Client, ClientRequestVO, ClientResponseVO> {

  protected static final String CLIENT_PATH = "/client";

  private final ClientService service;

  @Override
  protected BaseCrudService<Client, ClientRequestVO, ClientResponseVO> getService() {
    return service;
  }
}
