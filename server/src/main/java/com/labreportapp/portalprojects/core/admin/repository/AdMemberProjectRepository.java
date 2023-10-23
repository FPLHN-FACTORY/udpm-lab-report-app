package com.labreportapp.portalprojects.core.admin.repository;

import com.labreportapp.portalprojects.core.admin.model.request.AdFindProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdMemberAndRoleProjectResponse;
import com.labreportapp.portalprojects.core.admin.model.response.AdMemberProjectReponse;
import com.labreportapp.portalprojects.entity.MemberProject;
import com.labreportapp.portalprojects.repository.MemberProjectRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hieundph25894
 */
@Repository
public interface AdMemberProjectRepository extends MemberProjectRepository {

    @Query(" SELECT  me FROM MemberProject me")
    List<MemberProject> findAllMember();

    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY mp.last_modified_date DESC ) AS stt,
                     mp.id,
                     mp.member_id,
                     mp.project_id,
                     mp.role,
                     mp.status_work,
             FROM member_project mp 
             JOIN project pro ON pro.id = mp.project_id
             WHERE  
             ( :#{#rep.name} IS NULL 
                OR :#{#rep.name} LIKE '' 
                OR pro.name LIKE %:#{#rep.name}% )          
            """, nativeQuery = true)
    List<AdMemberProjectReponse> findByName(@Param("rep") AdFindProjectRequest rep);

    @Query(value = """
                SELECT ROW_NUMBER() OVER(ORDER BY mp.last_modified_date DESC ) AS stt,
                 		mp.member_id AS member_id,
                        mp.email AS email,
                        mp.status_work AS status_work
                FROM member_project mp
                WHERE mp.project_id = :idProject
            """, nativeQuery = true)
    List<AdMemberAndRoleProjectResponse> findAllMemberJoinProject(@Param("idProject") String idProject);

    //DISTINCT   mp.member_id AS member_id,
//                ROW_NUMBER() OVER(ORDER BY mp.last_modified_date DESC ) AS stt,
//                       mp.email AS email,
//                       mp.status_work AS status_work,
//                FROM member_project mp
//                JOIN role_member_project rmp ON mp.id = rmp.member_project_id
//                JOIN role_project rp ON rmp.role_project_id = rp.id
//                JOIN project p ON p.id = mp.project_id
//                 WHERE p.id = :idProject
    //    rp.name AS name_role_project,
//    rp.id AS role_project_id
    // countQuery = """
//              SELECT COUNT(DISTINCT mp. mp.member_id)
//                            FROM member_project mp
//                            JOIN role_member_project rmp ON mp.id = rmp.member_project_id
//                            JOIN role_project rp ON rmp.role_project_id = rp.id
//                            JOIN project p ON p.id = mp.project_id
//                             WHERE p.id = :idProject
//            """
    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY mp.last_modified_date DESC ) AS stt ,
                     mp.id,
                     mp.member_id,
                     mp.project_id,
                     mp.role,
                     mp.status_work,
                     pro.name
             FROM member_project mp 
             JOIN project pro ON pro.id = mp.project_id
             WHERE  
                  mp.member_id = :idMember  AND mp.project_id = :idProject
            """, nativeQuery = true)
    AdMemberProjectReponse getOne(@Param("idProject") String idProject, @Param("idMember") String idMember);

    @Query(value = "SELECT * FROM member_project" +
            " WHERE member_id = :memberId " +
            "AND project_id = :projectId", nativeQuery = true)
    MemberProject findMemberProject(@Param("memberId") String memberId, @Param("projectId") String projectId);

}
