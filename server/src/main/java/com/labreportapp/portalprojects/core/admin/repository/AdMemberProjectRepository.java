package com.labreportapp.portalprojects.core.admin.repository;

import com.labreportapp.portalprojects.core.admin.model.request.AdFindProjectRequest;
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
             SELECT ROW_NUMBER() OVER(ORDER BY mp.last_modified_date DESC ) AS STT ,
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
             SELECT ROW_NUMBER() OVER(ORDER BY mp.last_modified_date DESC ) AS STT ,
                     mp.id,
                     mp.member_id,
                     mp.project_id,
                     mp.role,
                     mp.status_work,
                     pro.name
             FROM member_project mp 
             JOIN project pro ON pro.id = mp.project_id
             WHERE  
                  pro.id = :idProject  AND  mp.status_work = '0'  
            """, nativeQuery = true)
    List<AdMemberProjectReponse> findAllMemberJoinProject(@Param("idProject") String idProject);

    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY mp.last_modified_date DESC ) AS STT ,
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
