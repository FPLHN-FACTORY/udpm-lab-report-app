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
public class MeDeleteDetailTodoRequest extends MeTodoAndTodoListRequest{

    @NotNull
    @NotBlank
    @NotEmpty
    private String id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String todoId;

    @NotNull
    @NotBlank
    @NotEmpty
    private String periodId;
}
