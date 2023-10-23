package com.labreportapp.portalprojects.core.admin.repository;

import com.labreportapp.portalprojects.core.admin.model.response.AdRoleMemberProjectDetailResponse;
import com.labreportapp.portalprojects.entity.RoleMemberProject;
import com.labreportapp.portalprojects.repository.RoleMemberProjectRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hieundph25894
 */
@Repository
public interface AdPotalsRoleMemberProjectRepository extends RoleMemberProjectRepository {

    @Query(value = """
                    SELECT * FROM role_member_project rmp 
                    JOIN role_project rp ON rp.id = rmp.role_project_id
                    WHERE rp.project_id = :#{#idProject}
            """, nativeQuery = true)
    List<RoleMemberProject> getListRoleMemberProjectByIdProj(@Param("idProject") String idProject);

    @Query(value = """
                    SELECT rmp.id as id, 
                        rmp.member_project_id as member_project_id,
                        rmp.role_project_id as role_project_id,
                        mp.member_id as member_id,
                        rp.name as name_role
                    FROM role_member_project rmp 
                    JOIN role_project rp ON rp.id = rmp.role_project_id
                    JOIN member_project mp ON mp.id = rmp.member_project_id
                    WHERE rp.project_id = :#{#idProject} and mp.project_id = :#{#idProject}
            """, nativeQuery = true)
    List<AdRoleMemberProjectDetailResponse> getAllRoleMemberCustomByIdProj(@Param("idProject") String idProject);

}
