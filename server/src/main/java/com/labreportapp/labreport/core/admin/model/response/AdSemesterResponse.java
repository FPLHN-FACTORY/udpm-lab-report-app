package com.labreportapp.labreport.core.admin.model.response;

import com.labreportapp.labreport.entity.Semester;
import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Semester.class})
public interface AdSemesterResponse extends IsIdentified {

    @Value("#{target.stt}")
    Integer STT();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.start_time_student}")
    Long getStartTimeStudent();

    @Value("#{target.end_time_student}")
    Long getEndTimeStudent();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.end_time}")
    Long getEndTime();

    @Value("#{target.status_feed_back}")
    Integer getStatusFeedback();
}
