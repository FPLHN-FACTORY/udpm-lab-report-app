package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.portalprojects.entity.RoleConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hieundph25894
 */
@Repository
public interface TeRoleConfigReposiotry extends JpaRepository<RoleConfig, String> {
}
