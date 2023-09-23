package com.labreportapp.labreport.core.student.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author quynhncph26201
 */
public interface StHomeWordAndNoteResponse {
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

//    @Value("#{target.idReport}")
//    String getIdReport();
//
//    @Value("#{target.descriptionsReport}")
//    String getDescriptionsReport();
}
