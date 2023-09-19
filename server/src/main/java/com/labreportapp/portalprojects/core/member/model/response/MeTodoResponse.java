package com.labreportapp.portalprojects.core.member.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import com.labreportapp.portalprojects.entity.Todo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {Todo.class})
public interface MeTodoResponse extends IsIdentified {

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.priority_level}")
    String getPriorityLevel();

    @Value("#{target.descriptions}")
    String getDescriptions();

    @Value("#{target.deadline}")
    Long getDeadline();

    @Value("#{target.completion_time}")
    Long getCompletionTime();

    @Value("#{target.index_todo}")
    Short getIndexTodo();

    @Value("#{target.progress}")
    Short getProgress();

    @Value("#{target.image_id}")
    String getImageId();

    @Value("#{target.name_file}")
    String getNameFile();

    @Value("#{target.number_todo_complete}")
    Short getNumberTodoComplete();

    @Value("#{target.number_todo}")
    Short getNumberTodo();

    @Value("#{target.todo_list_id}")
    String getTodoListId();
}
