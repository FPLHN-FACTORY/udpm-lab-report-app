package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.MemberProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(MemberProjectRepository.NAME)
public interface MemberProjectRepository extends JpaRepository<MemberProject, String> {

    public static final String NAME = "BaseMemberProjectRepository";

    @Query(value = """
            SELECT * FROM member_project 
            WHERE member_id = :memberId 
            AND project_id = :projectId
            """, nativeQuery = true)
    MemberProject findMemberByMemberIdAndProjectId(@Param("memberId") String memberId,
                                                   @Param("projectId") String projectId);
}
