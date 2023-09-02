package com.labreportapp.core.student.model.response;

import com.labreportapp.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface StListTeamInClassResponse extends IsIdentified {

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.subject_name}")
    String getSubjectName();
}
