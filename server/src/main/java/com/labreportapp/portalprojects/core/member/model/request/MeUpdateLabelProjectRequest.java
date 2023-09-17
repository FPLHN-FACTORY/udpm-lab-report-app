package com.labreportapp.portalprojects.core.member.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class MeUpdateLabelProjectRequest {

    @NotEmpty
    @NotBlank
    @NotNull
    private String id;

    @NotEmpty
    @NotBlank
    @NotNull
    private String name;

    @NotEmpty
    @NotBlank
    @NotNull
    private String color;
}
