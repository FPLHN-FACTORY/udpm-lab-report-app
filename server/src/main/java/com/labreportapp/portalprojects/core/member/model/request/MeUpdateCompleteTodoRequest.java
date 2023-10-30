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

    @NotBlank
    private String id;

    @NotNull
    private Short status;

    @NotBlank
    private String idTodo;

    @NotBlank
    private String idTodoList;

    @NotBlank
    private String projectId;

    @NotBlank
    private String periodId;

}
