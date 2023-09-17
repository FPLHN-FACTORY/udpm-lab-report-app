package com.labreportapp.portalprojects.core.common.base;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@Builder
public class TodoAndTodoListObject {

    private Object data;

    private Object dataActivity;

    private String idTodoListOld;

    private Integer indexBefore;

    private Integer indexAfter;

    private String sessionId;
}
