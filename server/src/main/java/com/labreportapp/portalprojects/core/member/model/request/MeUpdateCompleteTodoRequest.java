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
public class MeUpdateCompleteTodoRequest {

    @NotNull
    @NotBlank
    @NotEmpty
    private String id;

    @NotNull
    private Short status;

    @NotNull
    @NotBlank
    @NotEmpty
    private String idTodo;

    @NotNull
    @NotBlank
    @NotEmpty
    private String idTodoList;

    @NotNull
    @NotBlank
    @NotEmpty
    private String projectId;

    @NotNull
    @NotBlank
    @NotEmpty
    private String periodId;

    private String idUser;
}
