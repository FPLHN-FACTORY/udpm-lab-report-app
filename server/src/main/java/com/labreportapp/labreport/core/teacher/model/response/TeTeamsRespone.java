package com.labreportapp.labreport.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeTeamsRespone {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.subjectName}")
    String getSubjectName();

    @Value("#{target.createdDate}")
    Long getCreatedDate();

    @Value("#{target.project_id}")
    String getIdProject();

}
