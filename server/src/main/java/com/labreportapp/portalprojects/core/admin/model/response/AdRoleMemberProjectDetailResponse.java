package com.labreportapp.portalprojects.core.admin.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface AdRoleMemberProjectDetailResponse {

    @Value("#{target.id}")
    String getIdRoleMemberProject();

    @Value("#{target.member_project_id}")
    String getMemberProjectId();

    @Value("#{target.role_project_id}")
    String getRoleProjectId();

    @Value("#{target.member_id}")
    String getMemberId();

    @Value("#{target.name_role}")
    String getNameRole();

}
