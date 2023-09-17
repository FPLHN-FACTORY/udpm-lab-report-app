package com.labreportapp.labreport.core.teacher.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeUpdateTeamsRequest {

    @NotEmpty
    @NotBlank
    private String id;

    @NotEmpty
    @NotBlank
    private String code;

    @NotEmpty
    @NotBlank
    private String name;

    private String subjectName;

    private List<TeTeamUpdateStudentClassRequest> listStudentClasses;

    private List<TeTeamUpdateStudentClassRequest> listStudentClassesDeleteIdTeam;
}
