package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.portalprojects.entity.RoleProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hieundph25894
 */
@Repository
public interface TeRoleProjectRepository extends JpaRepository<RoleProject, String> {
}
