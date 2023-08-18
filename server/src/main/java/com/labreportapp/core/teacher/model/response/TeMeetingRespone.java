package com.labreportapp.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeMeetingRespone {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.descriptions}")
    String getDescriptions();

    @Value("#{target.meeting_date}")
    Long getMeetingDate();
}
