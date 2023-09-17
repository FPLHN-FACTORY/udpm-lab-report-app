package com.labreportapp.labreport.core.student.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author quynhncph26201
 */
public interface StPostResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.descriptions}")
    String getDescriptions();

    @Value("#{target.created_date}")
    Long getCreatedDate();
}
