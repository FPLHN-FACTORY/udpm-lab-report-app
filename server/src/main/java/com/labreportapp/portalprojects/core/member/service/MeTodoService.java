package com.labreportapp.portalprojects.core.member.service;

import com.labreportapp.portalprojects.core.common.base.TodoAndTodoListObject;
import com.labreportapp.portalprojects.core.common.base.TodoObject;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateDetailTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeDeleteDeadlineTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeDeleteDetailTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeDeleteTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeFilterTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeSortTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateCompleteTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateDeTailTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateDeadlineTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateDescriptionsTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateIndexTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateNameTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateProgressTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateStatusTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateTypeTodoRequest;
import com.labreportapp.portalprojects.core.member.model.response.*;
import com.labreportapp.portalprojects.entity.Todo;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface MeTodoService {

    List<MeBoardResponse> getBoard(final MeFilterTodoRequest request);

    MeAllDetailTodo getAllDetailTodo(String idTodo);

    MeTodoResponse findTodoById(@Param("idTodo") String idTodo);

    TodoObject updateNameTodo(@Valid MeUpdateNameTodoRequest request);

    List<MeConvertTodoResponse> filter(MeFilterTodoRequest request, String idPeriod, String idTodoList);

    String checkTodoFilter(MeFilterTodoRequest req, String idPeriod, String idTodoList, String idTodo);

    Todo findById(String id);

    List<MeDetailTodoResponse> getDetailTodo(String idTodo);

    TodoObject updatePriorityLevel(@Valid MeUpdateTodoRequest request, StompHeaderAccessor headerAccessor);

    TodoObject updateProgress(@Valid MeUpdateProgressTodoRequest request, StompHeaderAccessor headerAccessor);

    TodoObject createTodoChecklist(@Valid MeCreateDetailTodoRequest request);

    Todo updateTodoChecklist(@Valid MeUpdateDeTailTodoRequest request);

    TodoObject updateStatusTodo(@Valid MeUpdateStatusTodoRequest request);

    TodoObject deleteDetailTodo(@Valid MeDeleteDetailTodoRequest request);

    TodoObject updateDescriptionsTodo(@Valid final MeUpdateDescriptionsTodoRequest request, StompHeaderAccessor headerAccessor);

    TodoObject updateDeadlineTodo(@Valid MeUpdateDeadlineTodoRequest request, StompHeaderAccessor headerAccessor);

    TodoObject deleteDeadlineTodo(@Valid MeDeleteDeadlineTodoRequest request, StompHeaderAccessor headerAccessor);

    Todo createTodo(@Valid MeCreateTodoRequest request, StompHeaderAccessor headerAccessor);

    TodoAndTodoListObject updateIndexTodo(@Valid MeUpdateIndexTodoRequest request, StompHeaderAccessor headerAccessor);

    TodoObject updateCompleteTodo(@Valid MeUpdateCompleteTodoRequest request, StompHeaderAccessor headerAccessor);

    TodoAndTodoListObject updateIndexTodoViewTable(@Valid MeUpdateIndexTodoRequest request, StompHeaderAccessor headerAccessor);

    MeDeleteTodoResponse deleteTodo(@Valid MeDeleteTodoRequest request);

    String sortTodoPriority(@Valid MeSortTodoRequest request);

    String sortTodoDeadline(@Valid MeSortTodoRequest request);

    String sortTodoCreatedDate(@Valid MeSortTodoRequest request);

    String sortTodoName(@Valid MeSortTodoRequest request);

    String sortTodoProgress(@Valid MeSortTodoRequest request);

    TodoObject updateTypeTodo(@Valid MeUpdateTypeTodoRequest request);

    List<MeDataDashboardTodoListResoonse> countTodoByTodoListAllProject(String projectId);

    Integer countTodoByDueDateAllProject(String projectId, Integer statusTodo);

    Integer countTodoByNoDueDateAllProject(String projectId);

    List<MeDataDashboardMemberResponse> countTodoByMemberAllProject(String projectId);

    Integer countTodoByNoMemberAllProject(String projectId);

    List<MeDataDashboardLabelResponse> countTodoByLabelAllProject(String projectId);

    Integer countTodoByNoLabelAllProject(String projectId);

    //////////////////////////////////////////////////////////////////////////

    List<MeDataDashboardTodoListResoonse> countTodoByTodoListPeriod(String projectId, String periodId);

    Integer countTodoByDueDatePeriod(String projectId, String periodId, Integer statusTodo);

    Integer countTodoByNoDueDatePeriod(String projectId, String periodId);

    List<MeDataDashboardMemberResponse> countTodoByMemberPeriod(String projectId, String periodId);

    Integer countTodoByNoMemberPeriod(String projectId, String periodId);

    List<MeDataDashboardLabelResponse> countTodoByLabelPeriod(String projectId, String periodId);

    Integer countTodoByNoLabelPeriod(String projectId, String periodId);
}
