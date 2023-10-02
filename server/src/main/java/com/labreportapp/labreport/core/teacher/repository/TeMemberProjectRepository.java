package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.portalprojects.entity.MemberProject;
import com.labreportapp.portalprojects.repository.MemberProjectRepository;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeMemberProjectRepository extends MemberProjectRepository {

    List<MemberProject> findMemberProjectByProjectId(String idClass);
}
