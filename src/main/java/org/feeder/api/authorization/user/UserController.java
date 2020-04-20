package org.feeder.api.authorization.user;

import static org.feeder.api.authorization.user.UserController.USER_PATH;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.feeder.api.authorization.user.entity.User;
import org.feeder.api.authorization.user.service.UserService;
import org.feeder.api.authorization.user.vo.UserRequestVO;
import org.feeder.api.authorization.user.vo.UserResponseVO;
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
@RequestMapping(USER_PATH)
public class UserController {

  protected static final String USER_PATH = "/user";
  private final UserService service;
  protected static final String ID_PATH = "/{id}";

  @PostMapping
  public ResponseEntity<UserResponseVO> create(@Valid @RequestBody final UserRequestVO vo) {
    UUID id = UUID.randomUUID();
    return ResponseEntity.status(CREATED)
        .contentType(APPLICATION_JSON)
        .body(getService().create(vo, id));
  }

  @GetMapping(ID_PATH)
  public ResponseEntity<UserResponseVO> get(@PathVariable final UUID id) {
    return ResponseEntity.status(OK)
        .contentType(APPLICATION_JSON)
        .body(getService().get(id));
  }

  @GetMapping
  public ResponseEntity<Page<UserResponseVO>> getPage(@PageableDefault final Pageable pageable) {
    return ResponseEntity.status(OK)
        .contentType(APPLICATION_JSON)
        .body(getService().getAll(pageable));
  }

  @PutMapping(ID_PATH)
  public ResponseEntity<UserResponseVO> update(
      @PathVariable final UUID id,
      @Valid @RequestBody final UserRequestVO vo) {
    return ResponseEntity.status(OK)
        .contentType(APPLICATION_JSON)
        .body(getService().update(vo, id));
  }

  @DeleteMapping(ID_PATH)
  public ResponseEntity<UserResponseVO> delete(@PathVariable final UUID id) {
    getService().delete(id);
    return ResponseEntity.status(NO_CONTENT)
        .build();
  }

  protected BaseCrudService<User, UserRequestVO, UserResponseVO> getService() {
    return service;
  }
}
