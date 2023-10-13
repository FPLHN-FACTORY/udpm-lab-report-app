package com.labreportapp.labreport.core.student.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class StMyClassCustom {

    private String id;

    private Integer stt;

    private String code;

    private Long startTime;

    private String classPeriod;

    private Integer startHour;

    private Integer startMinute;

    private Integer endHour;

    private Integer endMinute;

    private String teacherId;

    private String userNameTeacher;

    private String nameTeacher;

    private String level;

    private String nameActivity;
}
