package com.labreportapp.core.admin.model.request;

import com.labreportapp.infrastructure.constant.Level;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AdBaseActivityRequest {

    @NotEmpty
    @NotBlank
    private String name;

    @NotEmpty
    @NotBlank
    private Long startTime;

    @NotEmpty
    @NotBlank
    private Long endTime;

    @NotEmpty
    @NotBlank
    private Level level;

    @NotEmpty
    @NotBlank
    private String semesterId;
}
