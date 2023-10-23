package com.labreportapp.portalprojects.core.admin.repository;

import com.labreportapp.labreport.infrastructure.constant.RoleDefault;
import com.labreportapp.portalprojects.entity.RoleProject;
import com.labreportapp.portalprojects.repository.RoleProjectRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author hieundph25894
 */
@Repository
public interface AdPotalsRoleProjectRepository extends RoleProjectRepository {

    Optional<RoleProject> findFirstByRoleDefault(RoleDefault roleDefault);

    List<RoleProject> findAllByProjectId(String idProject);
}
