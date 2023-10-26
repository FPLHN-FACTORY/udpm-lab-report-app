package com.labreportapp.portalprojects.core.admin.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface AdMemberAndRoleProjectResponse {

    Integer getStt();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.member_id}")
    String getMemberId();

    @Value("#{target.status_work}")
    Integer getStatus();

    @Value("#{target.id_member_project}")
    String getIdMemberProject();
//
//    @Value("#{target.name_role_project}")
//    String getNameRole();

}
