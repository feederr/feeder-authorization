package org.feeder.api.authorization.role.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.feeder.api.authorization.role.RoleMapper;
import org.feeder.api.authorization.role.RoleRepository;
import org.feeder.api.authorization.role.entity.Role;
import org.feeder.api.authorization.role.vo.RoleRequestVO;
import org.feeder.api.authorization.role.vo.RoleResponseVO;
import org.feeder.api.core.exception.EntityNotFoundException;
import org.feeder.api.core.mapper.BaseMapper;
import org.feeder.api.core.service.BaseCrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService extends BaseCrudService<Role, RoleRequestVO, RoleResponseVO> {

  private final RoleRepository repository;

  private final RoleMapper mapper;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Role getByRoleName(String roleName) {
    return repository.findByNameIgnoreCase(roleName)
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("%s by name = %s not found",
                getEntityClass().getSimpleName(), roleName), getEntityClass(), null)
        );
  }

  @Override
  protected BaseMapper<Role, RoleRequestVO, RoleResponseVO> getMapper() {
    return mapper;
  }

  @Override
  protected JpaRepository<Role, UUID> getRepository() {
    return repository;
  }

  @Override
  protected Class<Role> getEntityClass() {
    return Role.class;
  }
}
