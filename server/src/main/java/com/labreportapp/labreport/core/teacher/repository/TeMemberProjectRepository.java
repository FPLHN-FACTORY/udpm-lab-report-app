package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.portalprojects.entity.MemberProject;
import com.labreportapp.portalprojects.repository.MemberProjectRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author hieundph25894
 */
@Repository
public interface TeMemberProjectRepository extends MemberProjectRepository {

    List<MemberProject> findMemberProjectByProjectId(String idClass);

    Optional<MemberProject> findMemberProjectByMemberId(String idMember);

}
