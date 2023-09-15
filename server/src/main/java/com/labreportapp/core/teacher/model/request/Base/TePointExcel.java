package com.labreportapp.core.teacher.model.request.Base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
