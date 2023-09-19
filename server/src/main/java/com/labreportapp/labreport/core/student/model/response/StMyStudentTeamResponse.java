package com.labreportapp.labreport.core.student.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author quynhncph26201
 */
public interface StMyStudentTeamResponse extends IsIdentified {

    @Value("#{target.student_id}")
    String getStudentId();

    @Value("#{target.class_id}")
    String getClassId();

    @Value("#{target.team_id}")
    String getTeamId();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.role}")
    Integer getRole();

    @Value("#{target.status}")
    String getStatus();
}