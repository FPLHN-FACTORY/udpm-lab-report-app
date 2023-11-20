package com.labreportapp.labreport.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author todo thangncph26123
 */
public interface TePointCustomResponse {

    @Value("#{target.id_student}")
    String getIdStudent();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.final_point}")
    Double getFinalPoint();
}
