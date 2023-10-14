package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.RoleFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hieundph25894
 */
@Repository(RoleFactoryRepository.NAME)
public interface RoleFactoryRepository extends JpaRepository<RoleFactory, String> {

    String NAME = "BaseRoleFactoryRepository";
}
