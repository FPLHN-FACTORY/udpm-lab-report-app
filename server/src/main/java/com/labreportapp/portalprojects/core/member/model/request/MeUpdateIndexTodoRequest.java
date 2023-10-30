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
public class MeUpdateIndexTodoRequest {

    @NotBlank
    private String idTodo;

    @NotBlank
    private String idTodoListOld;

    @NotBlank
    private String nameTodoListOld;

    @NotBlank
    private String idTodoListNew;

    @NotBlank
    private String nameTodoListNew;

    private Short indexBefore;

    private Short indexAfter;

    @NotBlank
    private String periodId;

    @NotBlank
    private String projectId;

    private String sessionId;
}
