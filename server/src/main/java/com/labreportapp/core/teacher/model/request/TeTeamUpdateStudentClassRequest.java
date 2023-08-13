package com.labreportapp.core.teacher.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894
 */
@Getter
@Setter
@ToString
public class TeTeamUpdateStudentClassRequest {

    @NotEmpty
    @NotBlank
    private String idStudentClass;

    @NotBlank
    @NotEmpty
    private String role;

}
