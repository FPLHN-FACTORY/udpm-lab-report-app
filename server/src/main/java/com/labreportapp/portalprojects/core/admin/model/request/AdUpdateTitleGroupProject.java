package com.labreportapp.portalprojects.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class AdUpdateTitleGroupProject {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    private String descriptions;
}
