package com.labreportapp.portalprojects.core.member.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@Builder
public class MeDeleteTodoResponse {

    private String idTodo;

    private String idTodoList;
}
