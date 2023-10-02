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

    @NotEmpty
    @NotBlank
    @NotNull
    private String id;

    private String url;

    private String name;

    @NotEmpty
    @NotBlank
    @NotNull
    private String idTodo;

    @NotEmpty
    @NotBlank
    @NotNull
    private String idTodoList;

    @NotEmpty
    @NotBlank
    @NotNull
    private String projectId;

//    private String idUser;
}
