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
public class MeUpdateTodoListRequest {

    @NotNull
    @NotEmpty
    @NotBlank
    private String idTodoList;

    @NotBlank
    @NotEmpty
    @NotNull
    private String idProject;

    @NotNull
    @NotEmpty
    @NotBlank
    private String indexBefore;

    @NotNull
    @NotEmpty
    @NotBlank
    private String indexAfter;

    @NotNull
    @NotEmpty
    @NotBlank
    private String sessionId;
}
