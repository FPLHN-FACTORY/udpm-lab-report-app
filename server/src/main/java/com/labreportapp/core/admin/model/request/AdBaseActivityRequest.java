package com.labreportapp.core.admin.model.request;

import com.labreportapp.infrastructure.constant.Level;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public abstract class AdBaseActivityRequest {

    @NotEmpty
    @NotBlank
    private String name;

    @NotEmpty
    @NotBlank
    private String startTime;

    @NotEmpty
    @NotBlank
    private String endTime;

    @NotEmpty
    @NotBlank
    private String level;

    @NotEmpty
    @NotBlank
    private String semesterId;

}
