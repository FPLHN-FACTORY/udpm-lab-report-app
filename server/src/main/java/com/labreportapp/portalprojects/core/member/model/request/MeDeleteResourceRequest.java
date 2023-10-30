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
public class MeDeleteResourceRequest {

    @NotBlank
    private String id;

    private String url;

    private String name;

    @NotBlank
    private String idTodo;

    @NotBlank
    private String idTodoList;

    @NotBlank
    private String projectId;
}
