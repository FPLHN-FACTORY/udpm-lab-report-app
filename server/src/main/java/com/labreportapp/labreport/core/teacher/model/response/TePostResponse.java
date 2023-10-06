package com.labreportapp.labreport.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TePostResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.descriptions}")
    String getDescriptions();

    @Value("#{target.created_date}")
    Long getCreatedDate();

    @Value("#{target.teacher_id}")
    String getIdTeacher();

}
