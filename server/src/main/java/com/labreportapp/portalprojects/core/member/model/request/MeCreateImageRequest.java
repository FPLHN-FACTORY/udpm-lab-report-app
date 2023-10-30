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
    private String urlImage;

    @NotBlank
    private String nameFileOld;

    @NotBlank
    private String idTodo;

    @NotBlank
    private String idTodoList;

    @NotBlank
    private String projectId;
//
//    private String idUser;
}
