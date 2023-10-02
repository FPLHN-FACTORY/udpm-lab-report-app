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
public class MeDeleteDeadlineTodoRequest extends MeTodoAndTodoListRequest{

    @NotNull
    @NotBlank
    @NotEmpty
    private String idTodoDelete;

    @NotNull
    @NotBlank
    @NotEmpty
    private String projectId;

//    private String idUser;
}
