package com.labreportapp.core.teacher.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeTeamUpdateStudentClassRequest {

    @NotEmpty
    @NotBlank
    private String idStudentClass;

    @NotBlank
    @NotEmpty
    private String role;

}
