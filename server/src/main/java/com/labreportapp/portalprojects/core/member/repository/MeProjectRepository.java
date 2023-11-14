package com.labreportapp.portalprojects.core.member.repository;

import com.labreportapp.portalprojects.core.admin.model.response.AdProjectReponse;
import com.labreportapp.portalprojects.core.member.model.request.MeFindProjectRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeDetailProjectCateResponse;
import com.labreportapp.portalprojects.core.member.model.response.MeProjectResponse;
import com.labreportapp.portalprojects.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author thangncph26123
 */
public interface MeProjectRepository extends ProjectRepository {

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY a.created_date DESC ) AS stt,
            a.id, a.name, a.descriptions, a.start_time, a.end_time,a.status_project as status, a.progress,
                a.background_image, a.background_color, a.type_project as type_project,
                gp.name as name_group_project,
                gp.id as id_group_project ,
                GROUP_CONCAT(cate.name SEPARATOR ', ') as nameCategorys     
            FROM project a JOIN member_project b ON a.id = b.project_id 
            LEFT JOIN group_project gp on gp.id = a.group_project_id
            JOIN project_category pa ON pa.project_id = a.id
            JOIN category cate on pa.category_id = cate.id
            WHERE b.member_id = :#{#req.idUser}
            AND (:#{#req.nameProject} IS NULL OR :#{#req.nameProject} LIKE '' OR a.name LIKE %:#{#req.nameProject}%)
            AND (:#{#req.status} IS NULL OR :#{#req.status} LIKE '' OR a.status_project = :#{#req.status})
            AND 
                ( :#{#req.groupProjectId} IS NULL 
                OR :#{#req.groupProjectId} LIKE ''
                OR :#{#req.groupProjectId} LIKE '0' AND a.group_project_id IS NULL
                OR a.group_project_id LIKE :#{#req.groupProjectId}
                )
            AND (:#{#req.categoryId} IS NULL OR :#{#req.categoryId} LIKE '' OR cate.id = :#{#req.categoryId})
            GROUP BY a.id
            ORDER BY a.created_date DESC
            """, countQuery = """
            SELECT COUNT(1) 
            FROM project a JOIN member_project b ON a.id = b.project_id 
             LEFT JOIN group_project gp on gp.id = a.group_project_id
              JOIN project_category pa ON pa.project_id = a.id
            JOIN category cate on pa.category_id = cate.id
            WHERE b.member_id = :#{#req.idUser}
            AND (:#{#req.nameProject} IS NULL OR :#{#req.nameProject} LIKE '' OR a.name LIKE %:#{#req.nameProject}%)
            AND (:#{#req.status} IS NULL OR :#{#req.status} LIKE '' OR a.status_project = :#{#req.status})
            AND 
                ( :#{#req.groupProjectId} IS NULL 
                OR :#{#req.groupProjectId} LIKE ''
                OR :#{#req.groupProjectId} LIKE '0' AND a.group_project_id IS NULL
                OR a.group_project_id LIKE :#{#req.groupProjectId}
                )
             AND (:#{#req.categoryId} IS NULL OR :#{#req.categoryId} LIKE '' OR cate.id = :#{#req.categoryId})
            GROUP BY a.id
            ORDER BY a.created_date DESC
            """, nativeQuery = true)
    Page<MeProjectResponse> getAllProjectById(Pageable page, @Param("req") MeFindProjectRequest req);

//    @Query(value = """
//            SELECT DISTINCT pro.id,
//                   pro.name,
//                   pro.code,
//                   pro.descriptions,
//                   pro.status_project,
//                   pro.start_time,
//                   pro.end_time,
//                   pro.progress,
//                   pro.created_date,
//                   pro.background_image,
//                   pro.background_color,
//                   GROUP_CONCAT(cate.name SEPARATOR ', ') as nameCategorys,
//                    gp.name as name_group_project,
//                    gp.id as id_group_project
//             FROM project_category a
//             JOIN project pro on a.project_id = pro.id
//             JOIN category cate on a.category_id = cate.id
//             LEFT JOIN group_project gp on gp.id = pro.group_project_id
//            WHERE pro.id = :idProject
//            """, nativeQuery = true)
//    Optional<MeDetailProjectCateResponse> findOneProjectCategoryById(@Param("idProject") String idProject);

    @Query(value = """
            SELECT DISTINCT pro.id,
                   pro.name,
                   pro.code,
                   pro.descriptions,
                   pro.status_project,
                   pro.start_time,
                   pro.end_time,
                   pro.progress,
                   pro.created_date,
                   pro.background_image,
                   pro.background_color,
                   GROUP_CONCAT(cate.name SEPARATOR ', ') as nameCategorys,
                   gp.name as name_group_project,
                   gp.id as id_group_project
             FROM project pro
             LEFT JOIN project_category a on a.project_id = pro.id
             LEFT JOIN category cate on a.category_id = cate.id
             LEFT JOIN group_project gp on gp.id = pro.group_project_id
             WHERE pro.id = :idProject
            """, nativeQuery = true)
    Optional<MeDetailProjectCateResponse> findOneProjectCategoryById(@Param("idProject") String idProject);


}
