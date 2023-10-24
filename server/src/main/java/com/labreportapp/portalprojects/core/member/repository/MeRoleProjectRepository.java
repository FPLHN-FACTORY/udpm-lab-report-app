package com.labreportapp.portalprojects.core.member.repository;

import com.labreportapp.portalprojects.core.member.model.response.MeRoleProjectResponse;
import com.labreportapp.portalprojects.repository.RoleProjectRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface MeRoleProjectRepository extends RoleProjectRepository {

    @Query(value = """
            SELECT a.id, a.name, a.description, a.role_default FROM role_project a WHERE a.project_id = :idProject
            """, nativeQuery = true)
    List<MeRoleProjectResponse> getAllRoleProject(@Param("idProject") String idProject);

    @Query(value = """
            SELECT id FROM role_project WHERE role_default = 0 AND project_id = :idProject
            """, nativeQuery = true)
    String getRoleProjectDefault(@Param("idProject") String idProject);

    @Query(value = """
            SELECT COUNT(b.id) FROM role_project a JOIN role_member_project b ON a.id = b.role_project_id
            JOIN member_project c ON c.id = b.member_project_id WHERE a.id = :idRoleProject
            """, nativeQuery = true)
    Integer countRoleMemberProject(@Param("idRoleProject") String idRoleProject);

}
