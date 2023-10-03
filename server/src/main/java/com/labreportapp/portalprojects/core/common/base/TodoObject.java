package com.labreportapp.portalprojects.core.common.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author thangncph26123
 */

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class TodoObject {

    private Object data;
    private Object dataActivity;
    private Object dataImage;
    private String idTodoList;
    private String idTodo;
    private Short numberTodoComplete;
    private Short numberTodo;

}