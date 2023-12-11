package com.labreportapp.portalprojects.core.admin.repository;

import com.labreportapp.portalprojects.core.admin.model.request.AdFindGroupProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdDetailGroupProjectResponse;
import com.labreportapp.portalprojects.core.admin.model.response.AdGroupProjectResponse;
import com.labreportapp.portalprojects.repository.GroupProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thangncph26123
 */
@Repository
public interface AdGroupProjectRepository extends GroupProjectRepository {

    @Query(value = """
            SELECT a.id, a.name, a.description, a.background_image FROM group_project a 
            WHERE (:#{#req.name} IS NULL OR :#{#req.name} LIKE '' OR a.name LIKE %:#{#req.name}%)
            ORDER BY a.created_date DESC
            """,
            countQuery = """
                    SELECT COUNT(1) FROM group_project
                    """, nativeQuery = true)
    Page<AdGroupProjectResponse> getAllPage(Pageable pageable, @Param("req") AdFindGroupProjectRequest req);

    @Query(value = """
            SELECT a.id, a.name, a.description, a.background_image FROM group_project a 
            WHERE a.id = :id
            """, nativeQuery = true)
    AdGroupProjectResponse findGroupProjectById(@Param("id") String id);

    @Query(value = """
            SELECT id, name, descriptions, background_image, background_color
            FROM project WHERE group_project_id = :id ORDER BY created_date DESC
            """, nativeQuery = true)
    List<AdDetailGroupProjectResponse> getAllProject(@Param("id") String id);
}
