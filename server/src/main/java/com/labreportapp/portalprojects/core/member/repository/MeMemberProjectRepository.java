package com.labreportapp.portalprojects.core.member.repository;

import com.labreportapp.portalprojects.core.member.model.response.MeMemberProjectResponse;
import com.labreportapp.portalprojects.entity.MemberProject;
import com.labreportapp.portalprojects.repository.MemberProjectRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface MeMemberProjectRepository extends MemberProjectRepository {

    @Query(value = """
            SELECT a.id, a.member_id, a.status_work FROM member_project a WHERE a.project_id = :idProject
            """, nativeQuery = true)
    List<MeMemberProjectResponse> getAllMemberProject(@Param("idProject") String idProject);

    @Query(value = """
            SELECT DISTINCT c.id FROM member_project a JOIN role_member_project b ON a.id = b.member_project_id
            JOIN role_project c ON c.id = b.role_project_id
            WHERE a.id = :idMemberProject
            """, nativeQuery = true)
    List<String> getAllRoleProjectByIdMemberProject(@Param("idMemberProject") String idMemberProject);

    @Query(value = """
            SELECT * FROM member_project a WHERE a.project_id = :idProject AND a.member_id = :idMember
            """, nativeQuery = true)
    MemberProject findMemberProject(@Param("idProject") String idProject, @Param("idMember") String idMember);

    @Query(value = """
            SELECT b.student_id FROM team a 
            JOIN student_classes b ON a.id = b.team_id
            WHERE a.project_id = :idProject
            """, nativeQuery = true)
    List<String> getAllMemberTeam(@Param("idProject") String idProject);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM role_member_project WHERE member_project_id = :idMemberProject
            """, nativeQuery = true)
    void deleteRoleMemberProject(@Param("idMemberProject") String idMemberProject);

    @Query(value = """
            SELECT a.member_id FROM member_factory a ORDER BY a.created_date DESC
            """, nativeQuery = true)
    List<String> getAllMemberFactory();
}
