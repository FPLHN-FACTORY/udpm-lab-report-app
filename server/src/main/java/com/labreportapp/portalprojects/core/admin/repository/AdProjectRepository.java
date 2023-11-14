package com.labreportapp.portalprojects.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.request.AdFindProjectStatisticTopRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindProjectStatisticsRequest;
import com.labreportapp.labreport.core.admin.model.response.AdCountStatusProjectResponse;
import com.labreportapp.labreport.core.admin.model.response.AdProjectStatisticTopFiveResponse;
import com.labreportapp.labreport.core.admin.model.response.AdProjectStatisticsProgressTaskResponse;
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

//    @Query(value = """
//             SELECT ROW_NUMBER() OVER(ORDER BY pro.last_modified_date DESC ) AS stt,
//                    pro.id,
//                    pro.name,
//                    pro.code,
//                    pro.descriptions,
//                    pro.status_project,
//                    pro.start_time,
//                    pro.end_time,
//                    pro.progress,
//                    pro.created_date,
//                    pro.background_image,
//                    pro.background_color,
//                    GROUP_CONCAT(cate.name SEPARATOR ', ') as nameCategorys,
//                    gp.name as name_group_project,
//                    gp.id as id_group_project
//              FROM project_category a
//              JOIN project pro on a.project_id = pro.id
//              JOIN category cate on a.category_id = cate.id
//              LEFT JOIN group_project gp on gp.id = pro.group_project_id
//              WHERE pro.type_project = '0'
//            AND
//            ( :#{#rep.code} IS NULL
//                OR :#{#rep.code} LIKE ''
//                OR pro.code LIKE %:#{#rep.code}% )
//             AND
//             ( :#{#rep.name} IS NULL
//                OR :#{#rep.name} LIKE ''
//                OR pro.name LIKE %:#{#rep.name}% )
//              AND
//                 ( :#{#rep.startTimeLong} IS NULL and :#{#rep.endTimeLong} IS NULL
//                OR pro.start_time >= :#{#rep.startTimeLong} and  pro.end_time <= :#{#rep.endTimeLong} )
//             AND
//                 ( :#{#rep.statusProject} IS NULL
//                OR :#{#rep.statusProject} LIKE ''
//                OR pro.status_project LIKE :#{#rep.statusProject} )
//            AND
//                 ( :#{#rep.idCategory} IS NULL
//                OR :#{#rep.idCategory} LIKE ''
//                OR cate.id LIKE %:#{#rep.idCategory}% )
//            AND
//                ( :#{#rep.groupProjectId} IS NULL
//                OR :#{#rep.groupProjectId} LIKE ''
//                OR :#{#rep.groupProjectId} LIKE '0' AND pro.group_project_id IS NULL
//                OR pro.group_project_id LIKE :#{#rep.groupProjectId}
//                )
//            GROUP BY pro.id
//            ORDER BY pro.last_modified_date DESC
//            """, countQuery = """
//             SELECT COUNT(DISTINCT pro.id)
//             FROM project_category a
//             JOIN project pro on a.project_id = pro.id
//             JOIN category cate on a.category_id = cate.id
//             LEFT JOIN group_project gp on gp.id = pro.group_project_id
//            WHERE pro.type_project = '0'
//            AND
//            ( :#{#rep.code} IS NULL
//                OR :#{#rep.code} LIKE ''
//                OR pro.code LIKE %:#{#rep.code}% )
//             AND
//             ( :#{#rep.name} IS NULL
//                OR :#{#rep.name} LIKE ''
//                OR pro.name LIKE %:#{#rep.name}% )
//             AND
//                 ( :#{#rep.startTimeLong} IS NULL and :#{#rep.endTimeLong} IS NULL)
//                OR (:#{#rep.startTimeLong} <= pro.start_time AND  pro.end_time <= :#{#rep.endTimeLong} )
//             AND
//                 ( :#{#rep.statusProject} IS NULL
//                OR :#{#rep.statusProject} LIKE ''
//                OR pro.status_project LIKE :#{#rep.statusProject} )
//            AND
//                 ( :#{#rep.idCategory} IS NULL
//                OR :#{#rep.idCategory} LIKE ''
//                OR cate.id LIKE %:#{#rep.idCategory}% )
//            AND
//                ( :#{#rep.groupProjectId} IS NULL
//                OR :#{#rep.groupProjectId} LIKE ''
//                OR :#{#rep.groupProjectId} LIKE '0' AND pro.group_project_id IS NULL
//                OR pro.group_project_id LIKE :#{#rep.groupProjectId}
//                )
//            ORDER BY pro.last_modified_date DESC
//             """, nativeQuery = true)
//    Page<AdProjectReponse> findProjectPage(@Param("rep") AdFindProjectRequest rep, Pageable page);

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
              FROM project pro
              LEFT JOIN project_category a on a.project_id = pro.id
              LEFT JOIN category cate on a.category_id = cate.id
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
             FROM project pro
             LEFT JOIN project_category a on a.project_id = pro.id
             LEFT JOIN category cate on a.category_id = cate.id
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
                OR (:#{#rep.startTimeLong} <= pro.start_time AND  pro.end_time <= :#{#rep.endTimeLong} )
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
                 pro.id, pro.name,
                  pro.code,
                  pro.descriptions,
                  pro.status_project,
                  pro.start_time,
                  pro.end_time, pro.progress, pro.created_date, pro.background_image, pro.background_color,
                  GROUP_CONCAT(cate.name SEPARATOR ', ') as nameCategorys,
                  gp.name as name_group_project,
                  gp.id as id_group_project
                  FROM project pro
                  LEFT JOIN project_category a on a.project_id = pro.id
                  LEFT JOIN category cate on a.category_id = cate.id
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

    @Query(value = """
             WITH TodoCounts AS (
                 SELECT
                     tl.project_id,
                     COUNT(td.id) AS total_todo_count
                 FROM
                     todo_list tl
                 LEFT JOIN to_do td ON tl.id = td.todo_list_id
                 GROUP BY tl.project_id
             ),
             CompletedTodoCounts AS (
                 SELECT
                     tl.project_id,
                     COUNT(td.id) AS completed_todo_count
                 FROM
                     todo_list tl
                 LEFT JOIN to_do td ON tl.id = td.todo_list_id
                 WHERE td.status_todo IN (2, 3) AND td.todo_id IS NULL
                 GROUP BY tl.project_id
             )
             SELECT
                 p.id,
                    p.name as name_project,
                    p.code as code_project,
                    p.progress,
                 COALESCE(TC.total_todo_count, 0) AS total_todo_count,
                 COALESCE(CTC.completed_todo_count, 0) AS completed_todo_count
             FROM
                 project p
             LEFT JOIN TodoCounts TC ON p.id = TC.project_id
             LEFT JOIN CompletedTodoCounts CTC ON p.id = CTC.project_id
             WHERE p.type_project = '0' 
               AND
                 ( :#{#req.startTimeLong} IS NULL and :#{#req.endTimeLong} IS NULL)
                OR (:#{#req.startTimeLong} <= p.start_time AND  p.end_time <= :#{#req.endTimeLong} )
             ORDER BY p.end_time DESC
            """, nativeQuery = true)
    List<AdProjectStatisticsProgressTaskResponse> findAllProjectStatistic(@Param("req") AdFindProjectStatisticsRequest req);

    @Query(value = """
                WITH GroupProjectCount AS (
                SELECT
                     COALESCE(COUNT(id), 0) AS count_group_project
                FROM
                    group_project
            ), ProjectStatusCounts AS (
                SELECT
                    COALESCE(SUM(CASE WHEN p.status_project = 0 THEN 1 ELSE 0 END), 0) AS count_project_ending,
                    COALESCE(SUM(CASE WHEN p.status_project = 1 THEN 1 ELSE 0 END), 0) AS count_project_starting,
                    COALESCE(SUM(CASE WHEN p.status_project = 2 THEN 1 ELSE 0 END), 0) AS count_project_not_start
                FROM
                    project p
                WHERE p.type_project = '0' 
                    AND
                     ( :#{#req.startTimeLong} IS NULL and :#{#req.endTimeLong} IS NULL)
                    OR (:#{#req.startTimeLong} <= p.start_time AND  p.end_time <= :#{#req.endTimeLong} )
            )
            SELECT
                ps.count_project_ending,
                ps.count_project_starting,
                ps.count_project_not_start,
                gpc.count_group_project
            FROM
                ProjectStatusCounts ps
            CROSS JOIN
                GroupProjectCount gpc;
            """, nativeQuery = true)
    AdCountStatusProjectResponse countProjectStatistic(@Param("req") AdFindProjectStatisticsRequest req);

    @Query(value = """
            WITH MemberCounts AS (
                SELECT
                    mp.project_id,
                    COUNT(mp.id) AS member_count
                FROM member_project mp
                JOIN project p ON p.id = mp.project_id
                WHERE p.type_project = '0'
                    AND (
                        ( :#{#req.startTimeLong} IS NULL and :#{#req.endTimeLong} IS NULL)
                        OR (:#{#req.startTimeLong} <= p.start_time AND p.end_time <= :#{#req.endTimeLong})
                    )
                GROUP BY mp.project_id
            ),
            RankedProjects AS (
                SELECT
                    p.id AS id,
                    p.name AS name,
                    p.code AS code,
                    p.progress AS progress,
                    p.start_time AS startTime,
                    p.end_time AS endTime,
                    p.status_project AS status_project,
                    COALESCE(mc.member_count, 0) AS members_count
                FROM project p
                LEFT JOIN MemberCounts mc ON p.id = mc.project_id
                WHERE p.type_project = '0'
                    AND (
                        ( :#{#req.startTimeLong} IS NULL and :#{#req.endTimeLong} IS NULL)
                        OR (:#{#req.startTimeLong} <= p.start_time AND p.end_time <= :#{#req.endTimeLong})
                    )
             )
            SELECT
                ROW_NUMBER() OVER(ORDER BY
                    CASE
                        WHEN :#{#req.typeProject} = 1 THEN -rp.progress
                        WHEN :#{#req.typeProject} = 2 THEN rp.progress
                        WHEN :#{#req.typeProject} = 3 THEN -rp.members_count 
                        WHEN :#{#req.typeProject} = 4 THEN rp.members_count 
                        ELSE rp.progress
                    END
                ) AS stt,
                rp.id,
                rp.name,
                rp.code,
                rp.progress,
                rp.members_count,
                rp.startTime as startTime,
                rp.endTime as endTime,
                rp.status_project as status_project
            FROM RankedProjects rp
            LIMIT 5;
            """, nativeQuery = true)
    List<AdProjectStatisticTopFiveResponse> getProjectFindTop(@Param("req") AdFindProjectStatisticTopRequest req);
}
