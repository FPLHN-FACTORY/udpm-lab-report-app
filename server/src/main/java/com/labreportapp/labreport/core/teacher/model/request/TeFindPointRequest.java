package com.labreportapp.labreport.core.teacher.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeFindPointRequest {

    private String idClass;

    private String idStudent;

    private Double checkPointPhase1;

    private Double checkPointPhase2;

    private Double finalPoint;

    private Integer statusTeam;

}
