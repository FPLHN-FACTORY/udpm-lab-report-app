package com.labreportapp.portalprojects.core.admin.model.request;

/**
 * @author hieundph25894
 */

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AdBaseLabelRequest {

    @NotEmpty
    @NotBlank
    private String name;

    @NotEmpty
    @NotBlank
    private String colorLabel;


}
