package com.labreportapp.labreport.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    private String code;

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

    @NotNull
    private Integer allowUseTrello;

    private String descriptions;

}
