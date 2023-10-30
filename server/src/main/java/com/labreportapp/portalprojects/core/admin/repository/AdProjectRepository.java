package com.labreportapp.portalprojects.core.admin.repository;

import com.labreportapp.portalprojects.core.admin.model.request.AdFindProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdProjectReponse;
import com.labreportapp.portalprojects.entity.Project;
import com.labreportapp.portalprojects.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author hieundph25894
 */
@Repository
public interface AdProjectRepository extends ProjectRepository {

    @Query(" SELECT pro FROM Project pro ORDER BY pro.lastModifiedDate DESC")
    List<Project> findAllProject(Pageable pageable);

    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY pro.last_modified_date DESC ) AS stt,
                    pro.id,
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
              FROM project_category a
              JOIN project pro on a.project_id = pro.id
              JOIN category cate on a.category_id = cate.id
              LEFT JOIN group_project gp on gp.id = pro.group_project_id
              WHERE pro.type_project = '0'
            AND
            ( :#{#rep.code} IS NULL
                OR :#{#rep.code} LIKE ''
                OR pro.code LIKE %:#{#rep.code}% )
             AND
             ( :#{#rep.name} IS NULL
                OR :#{#rep.name} LIKE ''
                OR pro.name LIKE %:#{#rep.name}% )
              AND
                 ( :#{#rep.startTimeLong} IS NULL and :#{#rep.endTimeLong} IS NULL
                OR pro.start_time >= :#{#rep.startTimeLong} and  pro.end_time <= :#{#rep.endTimeLong} )
             AND
                 ( :#{#rep.statusProject} IS NULL
                OR :#{#rep.statusProject} LIKE ''
                OR pro.status_project LIKE :#{#rep.statusProject} )
            AND
                 ( :#{#rep.idCategory} IS NULL
                OR :#{#rep.idCategory} LIKE ''
                OR cate.id LIKE %:#{#rep.idCategory}% )
            AND 
                ( :#{#rep.groupProjectId} IS NULL 
                OR :#{#rep.groupProjectId} LIKE ''
                OR :#{#rep.groupProjectId} LIKE '0' AND pro.group_project_id IS NULL
                OR pro.group_project_id LIKE :#{#rep.groupProjectId}
                )
            GROUP BY pro.id
            ORDER BY pro.last_modified_date DESC
            """, countQuery = """
             SELECT COUNT(DISTINCT pro.id)
             FROM project_category a
             JOIN project pro on a.project_id = pro.id
             JOIN category cate on a.category_id = cate.id
             LEFT JOIN group_project gp on gp.id = pro.group_project_id
            WHERE pro.type_project = '0'
            AND
            ( :#{#rep.code} IS NULL
                OR :#{#rep.code} LIKE ''
                OR pro.code LIKE %:#{#rep.code}% )
             AND
             ( :#{#rep.name} IS NULL
                OR :#{#rep.name} LIKE ''
                OR pro.name LIKE %:#{#rep.name}% )
             AND
                 ( :#{#rep.startTimeLong} IS NULL and :#{#rep.endTimeLong} IS NULL)
                OR (pro.start_time >= :#{#rep.startTimeLong} AND  pro.end_time <= :#{#rep.endTimeLong} )
             AND
                 ( :#{#rep.statusProject} IS NULL
                OR :#{#rep.statusProject} LIKE ''
                OR pro.status_project LIKE :#{#rep.statusProject} )
            AND
                 ( :#{#rep.idCategory} IS NULL
                OR :#{#rep.idCategory} LIKE ''
                OR cate.id LIKE %:#{#rep.idCategory}% )
            AND 
                ( :#{#rep.groupProjectId} IS NULL 
                OR :#{#rep.groupProjectId} LIKE ''
                OR :#{#rep.groupProjectId} LIKE '0' AND pro.group_project_id IS NULL
                OR pro.group_project_id LIKE :#{#rep.groupProjectId}
                )
            ORDER BY pro.last_modified_date DESC
             """, nativeQuery = true)
    Page<AdProjectReponse> findProjectPage(@Param("rep") AdFindProjectRequest rep, Pageable page);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY pro.last_modified_date DESC ) AS stt,
                   pro.id,
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
             FROM project_category a
             JOIN project pro on a.project_id = pro.id
             JOIN category cate on a.category_id = cate.id
             LEFT JOIN group_project gp on gp.id = pro.group_project_id
            WHERE pro.id = :idProject
            """, nativeQuery = true)
    Optional<AdProjectReponse> findOneProjectById(@Param("idProject") String idProject);

    @Query(value = """
             SELECT pro.code FROM project pro  WHERE pro.code = :codeProject  
            """, nativeQuery = true)
    String getProjectByCode(@Param("codeProject") String codeProject);


    @Query("SELECT pro.id  FROM Project pro WHERE pro.code = :codeProject AND pro.id <> :id")
    String findByIdCode(@Param("codeProject") String codeProject,
                        @Param("id") String id);

//       ( :#{#rep.startTimeLong} IS NULL and :#{#rep.endTimeLong} IS NULL
//    OR pro.start_time >= :#{#rep.startTimeLong} and  pro.end_time <= :#{#rep.endTimeLong} )


}
