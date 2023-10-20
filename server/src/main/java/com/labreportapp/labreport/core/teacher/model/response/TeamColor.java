package com.labreportapp.labreport.core.teacher.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TeamColor {

    private String id;

    private String code;

    private String name;

    private String subjectName;

    private String classId;

    private String projectId;

    private short color;

    private Integer rowStartMeger;

    private Integer rowEndMeger;

}
