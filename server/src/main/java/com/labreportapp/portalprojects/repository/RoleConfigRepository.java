package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.RoleConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(RoleConfigRepository.NAME)
public interface RoleConfigRepository extends JpaRepository<RoleConfig, String> {

    String NAME = "BaseRoleConfigRepository";
}
