package com.labreportapp.labreport.core.teacher.model.response;


import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TePointResponse {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.id_studentClasses}")
    String getIdStudentClass();

    @Value("#{target.id_point}")
    String getIdPoint();

    @Value("#{target.id_student}")
    String getIdStudent();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.name_team}")
    String getNameTeam();

    @Value("#{target.check_point_phase1}")
    Double getCheckPointPhase1();

    @Value("#{target.check_point_phase2}")
    Double getCheckPointPhase2();

    @Value("#{target.final_point}")
    Double getFinalPoint();

    @Value("#{target.class_id}")
    String getIdClass();

    @Value("#{target.so_buoi_di}")
    Integer getSoBuoiDiHoc();

    @Value("#{target.so_buoi_hoc}")
    Integer getSoBuoiPhaiHoc();

}
