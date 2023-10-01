package com.labreportapp.labreport.core.teacher.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeStudentStatusApiResponse {

    private String idStudentClass;

    private String name;

    private String username;

    private String email;

    private String idStudent;

    private Integer statusTeam;

}
