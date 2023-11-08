package com.labreportapp.labreport.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdUpdateSemesterRequest extends AdBaseSemesterRequest {

    @NotBlank
    private String id;
}
