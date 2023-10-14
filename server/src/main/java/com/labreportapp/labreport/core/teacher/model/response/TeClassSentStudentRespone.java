package com.labreportapp.labreport.core.teacher.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeClassSentStudentRespone {

    private Integer stt;

    private String id;

    private String code;

    private String classPeriod;

    private Integer startHour;

    private Integer startMinute;

    private Integer endHour;

    private Integer endMinute;

    private Integer classSize;

    private String idTeacher;

    private String usernameTeacher;

}
