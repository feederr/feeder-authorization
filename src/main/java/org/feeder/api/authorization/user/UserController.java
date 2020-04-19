package org.feeder.api.authorization.user;

import static org.feeder.api.authorization.user.UserController.USER_PATH;

import lombok.RequiredArgsConstructor;
import org.feeder.api.authorization.user.entity.User;
import org.feeder.api.authorization.user.service.UserService;
import org.feeder.api.authorization.user.vo.UserRequestVO;
import org.feeder.api.authorization.user.vo.UserResponseVO;
import org.feeder.api.core.controller.BaseCrudController;
import org.feeder.api.core.service.BaseCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER_PATH)
public class UserController extends BaseCrudController<User, UserRequestVO, UserResponseVO> {

  protected static final String USER_PATH = "/user";

  private final UserService service;

  @Override
  protected BaseCrudService<User, UserRequestVO, UserResponseVO> getService() {
    return service;
  }
}
