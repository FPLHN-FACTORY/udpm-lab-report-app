package com.labreportapp.labreport.core.teacher.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TePostTeacherResponse {

    private String id;

    private String descriptions;

    private Long createdDate;

    private String teacherId;

    private String teacherUsername;

    private String teacherName;

}
