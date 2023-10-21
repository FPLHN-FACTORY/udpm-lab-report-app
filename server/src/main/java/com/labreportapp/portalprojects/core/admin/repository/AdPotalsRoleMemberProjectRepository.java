package com.labreportapp.portalprojects.core.admin.repository;

import com.labreportapp.portalprojects.entity.RoleMemberProject;
import com.labreportapp.portalprojects.repository.RoleMemberProjectRepository;
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

}
