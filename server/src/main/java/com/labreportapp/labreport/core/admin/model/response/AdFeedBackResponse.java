package com.labreportapp.labreport.core.admin.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author quynhncph26201
 */
public interface AdFeedBackResponse {
    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.descriptions}")
    String getDescription();

    @Value("#{target.idStudent}")
    String getIdStudent();

    @Value("#{target.class_id}")
    String getIdClass();
}
