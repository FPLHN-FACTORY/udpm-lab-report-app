package com.labreportapp.labreport.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdBaseClassConfigurationRequest {

    @NotNull
    private Integer classSizeMin;

    @NotNull
    private Integer classSizeMax;

    @NotNull
    private Double pointMin;

    @NotNull
    private Double maximumNumberOfBreaks;

    @NotNull
    private Integer numberHoney;


}
