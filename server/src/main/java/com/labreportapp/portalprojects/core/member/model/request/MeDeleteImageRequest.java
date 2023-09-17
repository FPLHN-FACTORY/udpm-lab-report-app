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
public class MeDeleteImageRequest {

    @NotNull
    @NotBlank
    @NotEmpty
    private String id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String nameFile;

    @NotNull
    @NotBlank
    @NotEmpty
    private String nameImage;

    @NotNull
    @NotBlank
    @NotEmpty
    private String statusImage;

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

    private String idUser;
}
