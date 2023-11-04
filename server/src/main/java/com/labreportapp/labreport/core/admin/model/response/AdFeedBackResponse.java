package com.labreportapp.labreport.core.admin.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author quynhncph26201
 */
public interface AdFeedBackResponse {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.rate_question1}")
    Integer getRateQuestion1();

    @Value("#{target.rate_question2}")
    Integer getRateQuestion2();

    @Value("#{target.rate_question3}")
    Integer getRateQuestion3();

    @Value("#{target.rate_question4}")
    Integer getRateQuestion4();

    @Value("#{target.rate_question5}")
    Integer getRateQuestion5();

    @Value("#{target.avergare_rate}")
    Float getAverageRate();

    @Value("#{target.descriptions}")
    String getDescription();

    @Value("#{target.idStudent}")
    String getIdStudent();

    @Value("#{target.class_id}")
    String getIdClass();

    @Value("#{target.created_date}")
    Long getCreatedDate();
}
