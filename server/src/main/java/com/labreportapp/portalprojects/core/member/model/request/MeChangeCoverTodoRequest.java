package com.labreportapp.portalprojects.core.member.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class MeChangeCoverTodoRequest {

    @NotNull
    @NotBlank
    @NotEmpty
    private String idTodo;

    @NotNull
    @NotBlank
    @NotEmpty
    private String idImage;

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(max = 100)
    private String nameFile;

    @NotNull
    @NotBlank
    @NotEmpty
    private String idTodoList;

    @NotNull
    @NotBlank
    @NotEmpty
    private String status;
}
