package com.labreportapp.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TePostRespone {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.descriptions}")
    String getDescriptions();

    @Value("#{target.created_date}")
    Long getCreatedDate();

}
