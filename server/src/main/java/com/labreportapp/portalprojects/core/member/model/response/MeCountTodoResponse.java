package com.labreportapp.portalprojects.core.member.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@AllArgsConstructor
public class MeCountTodoResponse {

    private Short numberTodoComplete;

    private Short numberTodo;
}
