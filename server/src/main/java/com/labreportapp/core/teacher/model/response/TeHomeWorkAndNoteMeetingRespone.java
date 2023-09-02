package com.labreportapp.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeHomeWorkAndNoteMeetingRespone {
    //idMeeting nameMeeting descriptionsMeeting         createdDate, idHomeWork descriptionsHomeWork idNote descriptionsNote
    @Value("#{target.idMeeting}")
    String getIdMeeting();

    @Value("#{target.nameMeeting}")
    String getNameMeeting();

    @Value("#{target.descriptionsMeeting}")
    String getDescriptionsMeeting();

//    @Value("#{target.idTeam}")
//    String getIdTeam();
//
//    @Value("#{target.codeTeam}")
//    String getCodeTeam();

//    @Value("#{target.nameTeam}")
//    String getNameTeam();
//
//    @Value("#{target.subjectName}")
//    String getSubjectName();

    //    @Value("#{target.class_id}")
    //   String getIdClass();
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
