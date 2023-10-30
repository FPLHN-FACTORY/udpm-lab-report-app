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

    @NotBlank
    private String idTodoList;

    @NotBlank
    private String idProject;

    @NotBlank
    private String indexBefore;

    @NotBlank
    private String indexAfter;

    @NotBlank
    private String sessionId;
}
