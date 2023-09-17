package com.labreportapp.portalprojects.core.admin.model.request;

/**
 * @author NguyenVinh
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
    private String code;

    @NotEmpty
    @NotBlank
    private String name;

    @NotEmpty
    @NotBlank
    private String colorLabel;


}
