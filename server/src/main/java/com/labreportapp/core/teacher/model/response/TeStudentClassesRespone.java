package com.labreportapp.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeStudentClassesRespone {

    @Value("#{target.idStudentClasses}")
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

}
