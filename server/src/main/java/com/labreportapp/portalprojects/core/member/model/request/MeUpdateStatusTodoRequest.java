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

    @NotBlank
    private String idTodoChange;

    private Integer statusTodo;

    @NotBlank
    private String periodId;

    @NotBlank
    private String todoId;

}
