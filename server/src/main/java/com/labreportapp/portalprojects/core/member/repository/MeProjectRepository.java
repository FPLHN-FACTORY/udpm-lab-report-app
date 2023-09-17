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
            SELECT a.id, a.name, a.descriptions, a.start_time, a.end_time,a.status_project as status, a.progress, a.background_image, a.background_color
            FROM project a JOIN member_project b ON a.id = b.project_id 
            WHERE b.member_id = :#{#req.idUser}
            AND (:#{#req.nameProject} IS NULL OR :#{#req.nameProject} LIKE '' OR a.name LIKE %:#{#req.nameProject}%)
            AND (:#{#req.status} IS NULL OR :#{#req.status} LIKE '' OR a.status_project = :#{#req.status})
            ORDER BY a.created_date DESC
            """, countQuery = """
            SELECT COUNT(1) 
            FROM project a JOIN member_project b ON a.id = b.project_id 
            WHERE b.member_id = :#{#req.idUser}
            AND (:#{#req.nameProject} IS NULL OR :#{#req.nameProject} LIKE '' OR a.name LIKE %:#{#req.nameProject}%)
            AND (:#{#req.status} IS NULL OR :#{#req.status} LIKE '' OR a.status_project = :#{#req.status})
            ORDER BY a.created_date DESC
            """, nativeQuery = true)
    Page<MeProjectResponse> getAllProjectById(Pageable page, @Param("req") MeFindProjectRequest req);

}
