package com.labreportapp.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty
    @NotBlank
    private Long startTime;

    @NotEmpty
    @NotBlank
    private Long endTime;
}
