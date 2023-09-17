package com.labreportapp.portalprojects.util;

import com.labreportapp.portalprojects.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author thangncph26123
 */
@Component
public class TodoListHelper {

    @Autowired
    @Qualifier(TodoListRepository.NAME)
    private TodoListRepository todoListRepository;

    public String genCodeTodoList(String projectId) {
        Integer countTodoList = todoListRepository.countSimpleEntityByIdProject(projectId);
        Integer newCountTodoList = ++countTodoList;
        return "TodoList_" + newCountTodoList;
    }

    public Byte genIndexTodoList(String projectId) {
        Byte indexMax = todoListRepository.getIndexTodoListMax(projectId);
        if(indexMax == null){
            return 0;
        }
        return ++indexMax;
    }
}
