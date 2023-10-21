package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.RoleMemberProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hieundph25894
 */
@Repository(RoleMemberProjectRepository.NAME)
public interface RoleMemberProjectRepository extends JpaRepository<RoleMemberProject, String> {

    String NAME = "BaseRoleMemberProjectRepository";
}
