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

    @NotEmpty
    @NotNull
    @NotBlank
    private String id;

    @NotEmpty
    @NotNull
    @NotBlank
    private String content;

    @NotEmpty
    @NotNull
    @NotBlank
    private String idTodo;

    @NotEmpty
    @NotNull
    @NotBlank
    private String idTodoList;

    @NotEmpty
    @NotNull
    @NotBlank
    private String memberId;
}
