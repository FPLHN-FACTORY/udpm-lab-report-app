package com.labreportapp.portalprojects.core.member.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@AllArgsConstructor
public class MeCreateImageRequest {

    @NotBlank
    @NotNull
    @NotEmpty
    private String urlImage;

    @NotBlank
    @NotNull
    @NotEmpty
    private String nameFileOld;

    @NotBlank
    @NotNull
    @NotEmpty
    private String idTodo;

    @NotBlank
    @NotNull
    @NotEmpty
    private String idTodoList;

    @NotBlank
    @NotNull
    @NotEmpty
    private String projectId;

    private String idUser;
}
