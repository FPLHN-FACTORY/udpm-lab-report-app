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

    @NotBlank
    private String id;

    @NotBlank
    private String nameFile;

    @NotBlank
    private String nameImage;

    @NotBlank
    private String statusImage;

    @NotBlank
    private String idTodo;

    @NotBlank
    private String idTodoList;

    @NotBlank
    private String projectId;
}
