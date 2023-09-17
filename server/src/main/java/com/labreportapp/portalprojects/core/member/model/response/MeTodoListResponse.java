package com.labreportapp.portalprojects.core.member.model.response;

import com.labreportapp.portalprojects.entity.TodoList;
import com.labreportapp.portalprojects.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {TodoList.class})
public interface MeTodoListResponse extends IsIdentified {

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.index_todo_list}")
    Byte getIndexTodoList();
}
