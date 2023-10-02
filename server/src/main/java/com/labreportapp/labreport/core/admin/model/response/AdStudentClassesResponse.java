package com.labreportapp.labreport.core.admin.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author quynhncph26201
 */
public interface AdStudentClassesResponse {
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

    @Value("#{target.codeTeam}")
    String getCodeTeam();

    @Value("#{target.nameTeam}")
    String getNameTeam();

    @Value("#{target.subject_name}")
    String getSubjectName();
}
