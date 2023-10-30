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
public class MeUpdateDeadlineTodoRequest extends MeTodoAndTodoListRequest{

    @NotNull
    private String idTodoUpdate;

    @NotBlank
    private String deadline;

    private String reminder;

    @NotBlank
    private String projectId;
}
