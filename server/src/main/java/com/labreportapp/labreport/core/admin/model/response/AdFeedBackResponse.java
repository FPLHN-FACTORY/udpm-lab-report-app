package com.labreportapp.labreport.core.admin.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author quynhncph26201
 */
public interface AdFeedBackResponse {
    @Value("#{target.stt}")
    Integer stt();

    @Value("#{target.descriptions}")
    String getDescriptions();

    @Value("#{target.student_id}")
    String getStudent_id();

}
