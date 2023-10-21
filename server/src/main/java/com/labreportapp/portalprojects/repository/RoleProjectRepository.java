package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.RoleProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hieundph25894
 */
@Repository(RoleProjectRepository.NAME)
public interface RoleProjectRepository extends JpaRepository<RoleProject, String> {

    String NAME = "BaseRoleProjectRepository";
}
