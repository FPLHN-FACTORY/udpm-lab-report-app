package com.labreportapp.labreport.core.student.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface StCheckFeedBackResponse extends IsIdentified {

    @Value("#{target.class_id}")
    String getClassId();

    @Value("#{target.student_id}")
    String getStudentId();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.role}")
    Integer getRole();

    @Value("#{target.status_student_feed_back}")
    Integer getStatusStudentFeedBack();

}
