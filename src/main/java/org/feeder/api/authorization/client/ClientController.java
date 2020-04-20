package org.feeder.api.authorization.client;

import static org.feeder.api.authorization.client.ClientController.CLIENT_PATH;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.feeder.api.authorization.client.entity.Client;
import org.feeder.api.authorization.client.service.ClientService;
import org.feeder.api.authorization.client.vo.ClientRequestVO;
import org.feeder.api.authorization.client.vo.ClientResponseVO;
import org.feeder.api.core.service.BaseCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(CLIENT_PATH)
public class ClientController {

  private static final String ID_PATH = "/{id}";
  protected static final String CLIENT_PATH = "/client";
  private final ClientService service;

  @PostMapping
  public ResponseEntity<ClientResponseVO> create(@Valid @RequestBody final ClientRequestVO vo) {
    UUID id = UUID.randomUUID();
    return ResponseEntity.status(CREATED)
        .contentType(APPLICATION_JSON)
        .body(service.create(vo, id));
  }

  @GetMapping(ID_PATH)
  public ResponseEntity<ClientResponseVO> get(@PathVariable final UUID id) {
    return ResponseEntity.status(OK)
        .contentType(APPLICATION_JSON)
        .body(service.get(id));
  }

  @GetMapping
  public ResponseEntity<Page<ClientResponseVO>> getPage(@PageableDefault final Pageable pageable) {
    return ResponseEntity.status(OK)
        .contentType(APPLICATION_JSON)
        .body(service.getAll(pageable));
  }

  @PutMapping(ID_PATH)
  public ResponseEntity<ClientResponseVO> update(
      @PathVariable final UUID id,
      @Valid @RequestBody final ClientRequestVO vo) {
    return ResponseEntity.status(OK)
        .contentType(APPLICATION_JSON)
        .body(service.update(vo, id));
  }

  @DeleteMapping(ID_PATH)
  public ResponseEntity<ClientResponseVO> delete(@PathVariable final UUID id) {
    service.delete(id);
    return ResponseEntity.status(NO_CONTENT)
        .build();
  }

  protected BaseCrudService<Client, ClientRequestVO, ClientResponseVO> service {
    return service;
  }
}
