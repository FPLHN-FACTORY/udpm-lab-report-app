package com.labreportapp.portalprojects.core.member.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class MeUpdateRoleProjectRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Integer roleDefault;

}
