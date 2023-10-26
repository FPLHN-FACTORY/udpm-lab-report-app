package com.labreportapp.portalprojects.core.member.repository;

import com.labreportapp.portalprojects.core.member.model.request.MeFindProjectRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeProjectResponse;
import com.labreportapp.portalprojects.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author thangncph26123
 */
public interface MeProjectRepository extends ProjectRepository {

    @Query(value = """
            SELECT a.id, a.name, a.descriptions, a.start_time, a.end_time,a.status_project as status, a.progress,
                a.background_image, a.background_color, a.type_project as type_project,
                gp.name as name_group_project,
                gp.id as id_group_project       
            FROM project a JOIN member_project b ON a.id = b.project_id 
             LEFT JOIN group_project gp on gp.id = a.group_project_id
            WHERE b.member_id = :#{#req.idUser}
            AND (:#{#req.nameProject} IS NULL OR :#{#req.nameProject} LIKE '' OR a.name LIKE %:#{#req.nameProject}%)
            AND (:#{#req.status} IS NULL OR :#{#req.status} LIKE '' OR a.status_project = :#{#req.status})
            AND 
                ( :#{#req.groupProjectId} IS NULL 
                OR :#{#req.groupProjectId} LIKE ''
                OR :#{#req.groupProjectId} LIKE '0' AND a.group_project_id IS NULL
                OR a.group_project_id LIKE :#{#req.groupProjectId}
                )
            ORDER BY a.created_date DESC
            """, countQuery = """
            SELECT COUNT(1) 
            FROM project a JOIN member_project b ON a.id = b.project_id 
             LEFT JOIN group_project gp on gp.id = a.group_project_id
            WHERE b.member_id = :#{#req.idUser}
            AND (:#{#req.nameProject} IS NULL OR :#{#req.nameProject} LIKE '' OR a.name LIKE %:#{#req.nameProject}%)
            AND (:#{#req.status} IS NULL OR :#{#req.status} LIKE '' OR a.status_project = :#{#req.status})
            AND 
                ( :#{#req.groupProjectId} IS NULL 
                OR :#{#req.groupProjectId} LIKE ''
                OR :#{#req.groupProjectId} LIKE '0' AND a.group_project_id IS NULL
                OR a.group_project_id LIKE :#{#req.groupProjectId}
                )
            ORDER BY a.created_date DESC
            """, nativeQuery = true)
    Page<MeProjectResponse> getAllProjectById(Pageable page, @Param("req") MeFindProjectRequest req);

}
