package com.labreportapp.core.student.model.response;

import com.labreportapp.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;


public interface StPointAllRespone extends IsIdentified {
    @Value("#{target.check_point_phase1}")
    Double getCheckPointPhase1();

    @Value("#{target.check_point_phase2}")
    Double getCheckPointPhase2();

    @Value("#{target.final_point}")
    Double getFinalPoint();

}
