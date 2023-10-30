package com.labreportapp.labreport.core.admin.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894 - duchieu212
 */
public interface AdCountStatusProjectResponse {

    @Value("#{target.count_group_project}")
    Integer getCountGroupProject();

    @Value("#{target.count_project_not_start}")
    Integer getCountProjectNotStart();

    @Value("#{target.count_project_starting}")
    Integer getCountProjectStarting();

    @Value("#{target.count_project_ending}")
    Integer getCountProjectEnding();

}
