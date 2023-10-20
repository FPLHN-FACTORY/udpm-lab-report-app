package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.core.admin.model.response.AdRoleFactoryDefaultResponse;
import com.labreportapp.labreport.entity.MemberRoleFactory;
import com.labreportapp.labreport.entity.RoleFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(MemberRoleFactoryRepository.NAME)
public interface MemberRoleFactoryRepository extends JpaRepository<MemberRoleFactory, String> {

    String NAME = "BaseMemberRoleFactoryRepository";

    @Query(value = """
            SELECT id, name, descriptions, role_default FROM role_factory WHERE role_default = 0
            """, nativeQuery = true)
    AdRoleFactoryDefaultResponse findRoleDefault();
}
