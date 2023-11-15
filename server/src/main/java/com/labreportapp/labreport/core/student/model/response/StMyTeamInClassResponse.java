package com.labreportapp.labreport.core.student.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author quynhncph26201
 */
public interface StMyTeamInClassResponse extends IsIdentified {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.subject_name}")
    String getSubjectName();

    @Value("#{target.class_id}")
    String getClassId();
}
