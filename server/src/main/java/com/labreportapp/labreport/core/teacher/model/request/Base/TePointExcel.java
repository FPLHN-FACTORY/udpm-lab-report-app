package com.labreportapp.labreport.core.teacher.model.request.Base;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TePointExcel {

    private String idPoint;

    private String idStudent;

    private String name;

    private String email;

    private Double checkPointPhase1;

    private Double checkPointPhase2;

    private Double finalPoint;

}
