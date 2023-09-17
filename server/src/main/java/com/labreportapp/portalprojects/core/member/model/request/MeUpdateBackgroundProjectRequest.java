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
public class MeUpdateBackgroundProjectRequest {

    @NotNull
    @NotBlank
    @NotEmpty
    private String projectId;

    @NotNull
    @NotBlank
    @NotEmpty
    private String name;

    @NotNull
    @NotBlank
    @NotEmpty
    private String type;

}
