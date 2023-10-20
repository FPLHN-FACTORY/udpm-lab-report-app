package com.labreportapp.labreport.core.teacher.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author hieundph25894
 */
@Getter
@Setter
@ToString
public class TeStudentCallApiResponse {

    private String id;

    private String name;

    private String username;

    private String email;

    private String idStudent;

    private String idStudentClass;

    private String role;

    private String statusStudent;

    private String idTeam;

    private String codeTeam;

    private String nameTeam;

    private String subjectName;

    private List<TeHwNoteReportListRespone> listHomeWorkNoteReport;
}
