package com.labreportapp.labreport.core.teacher.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author todo hieundph25894 - duchieu212
 */
@Getter
@Setter
@ToString
public class TeFindClassSelectRequest {

    private String idSemester;

    private String idActivity;

    private String idTeacher;

}
