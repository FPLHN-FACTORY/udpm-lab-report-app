package com.labreportapp.labreport.core.admin.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author quynhncph26201
 */
public interface AdTeamResponse {
    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.subjectName}")
    String getSubjectName();

    @Value("#{target.createdDate}")
    Long getCreatedDate();

    @Value("#{target.project_id}")
    String getIdProject();

    @Value("#{target.class_id}")
    String getIdClass();
}
