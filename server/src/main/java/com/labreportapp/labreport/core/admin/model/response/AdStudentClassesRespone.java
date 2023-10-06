package com.labreportapp.labreport.core.admin.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface AdStudentClassesRespone {
    @Value("#{target.idStudentClass}")
    String getIdStudentClass();

    @Value("#{target.idStudent}")
    String getIdStudent();

    @Value("#{target.emailStudent}")
    String getEmailStudentClass();

    @Value("#{target.role}")
    String getRole();

    @Value("#{target.statusStudent}")
    String getStatusStudent();

    @Value("#{target.idTeam}")
    String getIdTeam();

    @Value("#{target.idFeedBack}")
    String getIdFeedBack();

    @Value("#{target.idAttendance}")
    String getIdAttendance();

    @Value("#{target.codeTeam}")
    String getCodeTeam();

    @Value("#{target.nameTeam}")
    String getNameTeam();
}
