package com.labreportapp.labreport.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdBaseClassConfigurationRequest {
    @NotEmpty
    @NotBlank
    private Integer classSizeMax;
}
