package com.labreportapp.labreport.core.admin.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894 - duchieu212
 */
public interface AdProjectStatisticsProgressTaskResponse extends IsIdentified {

    @Value("#{target.name_project}")
    String getName();

    @Value("#{target.code_project}")
    String getCode();

    @Value("#{target.progress}")
    Float getProgress();

    @Value("#{target.total_todo_count}")
    Integer getTotalTasks();

    @Value("#{target.completed_todo_count}")
    Integer getCompletedTasks();

}
