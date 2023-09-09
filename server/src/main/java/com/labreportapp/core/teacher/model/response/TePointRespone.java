package com.labreportapp.core.teacher.model.response;


import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TePointRespone {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.idStudent}")
    String getIdStudent();

    @Value("#{target.check_point_phase1}")
    String getCheckPointPhase1();

    @Value("#{target.check_point_phase2}")
    String getCheckPointPhase2();

     @Value("#{target.final_point}")
    String getFinalPoint();

    @Value("#{target.class_id}")
    String getIdClass();

}
