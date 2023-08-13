package com.labreportapp.core.teacher.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class TeCreateTeamsRequest {

    private String classId;

    @NotEmpty
    @NotBlank
    private String code;

    @NotEmpty
    @NotBlank
    private String name;

    @NotEmpty
    @NotBlank
    private String subjectName;

    private List<TeTeamUpdateStudentClassRequest> listStudentClasses;

}
