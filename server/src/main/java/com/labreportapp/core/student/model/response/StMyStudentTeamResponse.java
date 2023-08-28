package com.labreportapp.core.student.model.response;

import com.labreportapp.entity.base.IsIdentified;
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
    String getRole();

    @Value("#{target.status}")
    String getStatus();
}
