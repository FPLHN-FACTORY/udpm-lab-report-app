package com.labreportapp.labreport.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class AdBaseSemesterRequest {

    @NotEmpty
    @NotBlank
    @Length(max = 500)
    private String name;

    @NotNull
    private Long startTimeStudent;

    @NotNull
    private Long endTimeStudent;

    @NotNull
    private Long startTime;

    @NotNull
    private Long endTime;
}
