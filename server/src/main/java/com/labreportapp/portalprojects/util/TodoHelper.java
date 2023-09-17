package com.labreportapp.portalprojects.util;

import com.labreportapp.portalprojects.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author thangncph26123
 */
@Component
public class TodoHelper {

    @Autowired
    @Qualifier(TodoRepository.NAME)
    private TodoRepository todoRepository;

    public String genCodeTodo(String todoListId) {
        Integer countTodo = todoRepository.countSimpleEntityByIdTodo(todoListId);
        Integer newCountTodo = ++countTodo;
        return "Todo_" + newCountTodo;
    }

    public Short genIndexTodo(String todoListId, String periodId) {
        Short indexMax = todoRepository.getIndexTodoMax(todoListId, periodId);
        if(indexMax == null){
            return 0;
        }
        return ++indexMax;
    }

    public float sumProgressAllTodoByProject(String projectId) {
        return todoRepository.getAllTodoInProject(projectId);
    }
}
