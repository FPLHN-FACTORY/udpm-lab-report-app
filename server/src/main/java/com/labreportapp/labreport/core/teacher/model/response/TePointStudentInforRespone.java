package com.labreportapp.labreport.core.teacher.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TePointStudentInforRespone {

    private Integer stt;

    private String idStudentClasses;

    private Integer statusTeam;

    private String idPoint;

    private String idStudent;

    private String username;

    private String nameStudent;

    private String emailStudent;

    private String nameTeam;

    private Double checkPointPhase1;

    private Double checkPointPhase2;

    private Double finalPoint;

    private String idClass;

    private Integer numberOfSessionAttended;

    private Integer numberOfSession;

    private Double pointMin;

    private Double maximumNumberOfBreaks;

}
