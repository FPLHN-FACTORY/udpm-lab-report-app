package com.labreportapp.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeDetailMeetingRespone {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();
    
}
