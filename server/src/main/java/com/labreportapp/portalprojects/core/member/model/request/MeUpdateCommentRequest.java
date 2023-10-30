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
public class MeUpdateCommentRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String content;

    @NotBlank
    private String idTodo;

    @NotBlank
    private String idTodoList;

}
