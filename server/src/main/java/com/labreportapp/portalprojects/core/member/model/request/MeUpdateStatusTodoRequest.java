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
public class MeUpdateStatusTodoRequest extends MeTodoAndTodoListRequest {

    @NotNull
    @NotBlank
    @NotEmpty
    private String idTodoChange;

    private Integer statusTodo;

    @NotNull
    @NotBlank
    @NotEmpty
    private String periodId;

    @NotNull
    @NotBlank
    @NotEmpty
    private String todoId;

}
