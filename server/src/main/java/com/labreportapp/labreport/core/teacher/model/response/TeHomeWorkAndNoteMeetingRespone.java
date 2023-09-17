package com.labreportapp.labreport.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeHomeWorkAndNoteMeetingRespone {

    @Value("#{target.idMeeting}")
    String getIdMeeting();

    @Value("#{target.nameMeeting}")
    String getNameMeeting();

    @Value("#{target.descriptionsMeeting}")
    String getDescriptionsMeeting();

    @Value("#{target.createdDate}")
    Long getCreatedDate();

    @Value("#{target.idHomeWork}")
    String getIdHomeWork();

    @Value("#{target.descriptionsHomeWork}")
    String getDescriptionsHomeWork();

    @Value("#{target.idNote}")
    String getIdNote();

    @Value("#{target.descriptionsNote}")
    String getDescriptionsNote();

}
