package com.labreportapp.portalprojects.core.admin.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import com.labreportapp.portalprojects.entity.MemberProject;
import com.labreportapp.portalprojects.entity.Project;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author NguyenVinh
 */
@Projection(types = {MemberProject.class , Project.class})
public interface AdMemberProjectReponse extends IsIdentified {

     Integer getSTT();

     @Value("#{target.member_id}")
     String getMemberId();

     @Value("#{target.project_id }")
     String getProjectId();

     @Value("#{target.role}")
     String getRole();

     @Value("#{target.status_work}")
     String getStatus();

     @Value("#{target.name}")
     String getNameMember();
}
