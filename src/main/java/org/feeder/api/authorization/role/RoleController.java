package org.feeder.api.authorization.role;

import static org.feeder.api.authorization.role.RoleController.ROLE_PATH;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.feeder.api.authorization.role.service.RoleService;
import org.feeder.api.authorization.role.vo.RoleRequestVO;
import org.feeder.api.authorization.role.vo.RoleResponseVO;
import org.feeder.api.core.util.UUIDUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROLE_PATH)
public class RoleController {

  protected static final String ROLE_PATH = "/role";

  private static final String ID_PATH = "/{id}";

  private final RoleService service;

  @PostMapping
  public ResponseEntity<RoleResponseVO> create(@Valid @RequestBody final RoleRequestVO vo) {
    UUID id = UUIDUtils.optimizedUUID();
    return ResponseEntity.status(CREATED)
        .contentType(APPLICATION_JSON)
        .body(service.create(vo, id));
  }

  @GetMapping(ID_PATH)
  public ResponseEntity<RoleResponseVO> get(@PathVariable final UUID id) {
    return ResponseEntity.status(OK)
        .contentType(APPLICATION_JSON)
        .body(service.get(id));
  }

  @GetMapping
  public ResponseEntity<Page<RoleResponseVO>> getPage(
      @RequestParam(name = "q", required = false) String predicate,
      @PageableDefault final Pageable pageable) {
    return ResponseEntity.status(OK)
        .contentType(APPLICATION_JSON)
        .body(service.getAllBySpec(predicate, pageable));
  }

  @PutMapping(ID_PATH)
  public ResponseEntity<RoleResponseVO> update(
      @PathVariable final UUID id,
      @Valid @RequestBody final RoleRequestVO vo) {
    return ResponseEntity.status(OK)
        .contentType(APPLICATION_JSON)
        .body(service.update(vo, id));
  }

  @DeleteMapping(ID_PATH)
  public ResponseEntity<RoleResponseVO> delete(@PathVariable final UUID id) {
    service.delete(id);
    return ResponseEntity.status(NO_CONTENT)
        .build();
  }
}
