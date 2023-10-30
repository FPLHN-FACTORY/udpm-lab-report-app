package com.labreportapp.labreport.core.admin.model.response;


import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894 - duchieu212
 */
public interface AdProjectStatisticTopFiveResponse {

    Integer getStt();

    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.progress}")
    Float getProgress();

    @Value("#{target.members_count}")
    Integer getMemberCount();

    @Value("#{target.startTime}")
    Long getStartTime();

    @Value("#{target.endTime}")
    Long getEndTime();

}
