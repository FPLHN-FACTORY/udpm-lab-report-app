package com.labreportapp.labreport.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894 - duchieu212
 */
public interface TeFindClassSelectResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.code}")
    String getCode();
}
