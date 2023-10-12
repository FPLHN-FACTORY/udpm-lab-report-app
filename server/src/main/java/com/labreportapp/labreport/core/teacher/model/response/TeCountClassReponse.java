package com.labreportapp.labreport.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeCountClassReponse {

    @Value("#{target.class_lesson}")
    Integer getClassLesson();

    @Value("#{target.class_number}")
    Integer getClassNumber();

}
