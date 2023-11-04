package com.labreportapp.portalprojects.core.member.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface MeDetailProjectCateResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.descriptions}")
    String getDescriptions();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.end_time}")
    Long getEndTime();

    @Value("#{target.progress}")
    Float getProgress();

    @Value("#{target.created_date}")
    Long getCreateDate();

    @Value("#{target.status_project}")
    String getStatusProject();

    @Value("#{target.background_image}")
    String getBackGroundImage();

    @Value("#{target.background_color}")
    String getBackGroundColor();

    @Value("#{target.nameCategorys}")
    String getNameCategorys();

    @Value("#{target.name_group_project}")
    String getNameGroupProject();

    @Value("#{target.id_group_project}")
    String getGroupProjectId();

}
