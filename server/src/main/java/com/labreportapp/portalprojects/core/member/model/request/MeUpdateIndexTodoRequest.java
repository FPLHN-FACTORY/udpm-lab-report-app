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

    @NotNull
    @NotBlank
    @NotEmpty
    private String idTodo;

    @NotNull
    @NotBlank
    @NotEmpty
    private String idTodoListOld;

    @NotNull
    @NotBlank
    @NotEmpty
    private String nameTodoListOld;

    @NotNull
    @NotBlank
    @NotEmpty
    private String idTodoListNew;

    @NotNull
    @NotBlank
    @NotEmpty
    private String nameTodoListNew;

    private Short indexBefore;

    private Short indexAfter;

    @NotNull
    @NotBlank
    @NotEmpty
    private String periodId;

    @NotNull
    @NotBlank
    @NotEmpty
    private String projectId;

    private String idUser;

    private String sessionId;
}
