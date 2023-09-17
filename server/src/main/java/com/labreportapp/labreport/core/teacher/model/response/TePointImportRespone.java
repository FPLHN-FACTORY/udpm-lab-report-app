package com.labreportapp.labreport.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TePointImportRespone {

    @Value("#{target.id}")
    String getIdStudentClass();

    @Value("#{target.idStudent}")
    String getIdStudent();

    @Value("#{target.emailStudent}")
    String getEmailStudent();

}
