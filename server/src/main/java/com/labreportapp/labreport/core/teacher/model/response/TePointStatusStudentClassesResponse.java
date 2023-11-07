package com.labreportapp.labreport.core.teacher.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TePointStatusStudentClassesResponse {

    private String idPoint;

    private Double checkPointPhase1;

    private Double checkPointPhase2;

    private Double finalPoint;

    private String studentId;

    private String classId;

    private Integer statusTeam;
    
}
