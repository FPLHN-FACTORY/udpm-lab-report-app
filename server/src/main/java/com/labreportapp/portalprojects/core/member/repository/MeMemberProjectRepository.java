package com.labreportapp.portalprojects.core.member.repository;

import com.labreportapp.portalprojects.core.member.model.response.MeMemberProjectResponse;
import com.labreportapp.portalprojects.entity.MemberProject;
import com.labreportapp.portalprojects.repository.MemberProjectRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface MeMemberProjectRepository extends MemberProjectRepository {

    @Query(value = """
            SELECT a.id, a.member_id, a.role, a.status_work FROM member_project a WHERE a.project_id = :idProject
            """, nativeQuery = true)
    List<MeMemberProjectResponse> getAllMemberProject(@Param("idProject") String idProject);

    @Query(value = """
            SELECT * FROM member_project a WHERE a.project_id = :idProject AND a.member_id = :idMember
            """, nativeQuery = true)
    MemberProject findMemberProject(@Param("idProject") String idProject, @Param("idMember") String idMember);
}
