package com.labreportapp.portalprojects.core.member.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import com.labreportapp.portalprojects.entity.Todo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {Todo.class})
public interface MeDetailTodoResponse extends IsIdentified {

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.status_todo}")
    Integer getStatusTodo();
}
