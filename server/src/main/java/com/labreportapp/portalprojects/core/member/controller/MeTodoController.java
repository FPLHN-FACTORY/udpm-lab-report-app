package com.labreportapp.portalprojects.core.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import com.labreportapp.portalprojects.core.member.model.request.DesVarProjectIdAndPeriodIdRequest;
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
import com.labreportapp.portalprojects.core.member.service.MeTodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/member/todo")
@CrossOrigin("*")
public class MeTodoController {

    @Autowired
    private MeTodoService meTodoService;

    @PostMapping
    public ResponseObject getBoard(@RequestBody MeFilterTodoRequest request) {
        return new ResponseObject(meTodoService.getBoard(request));
    }

    @GetMapping("/find-todo")
    public ResponseObject findTodoById(@RequestParam("idTodo") String idTodo) {
        return new ResponseObject(meTodoService.findTodoById(idTodo));
    }

    @GetMapping("/filter")
    public ResponseObject filter(@RequestParam("idPeriod") String idPeriod,
                                 @RequestParam("idTodoList") String idTodoList,
                                 @RequestParam("filter") String filter) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(filter);
        MeFilterTodoRequest request = objectMapper.readValue(filter, MeFilterTodoRequest.class);
        System.out.println(request);
        return new ResponseObject(meTodoService.filter(request, idPeriod, idTodoList));
    }

    @GetMapping("/check-filter")
    public ResponseObject checkFilter(@RequestParam("idPeriod") String idPeriod,
                                      @RequestParam("idTodoList") String idTodoList,
                                      @RequestParam("idTodo") String idTodo,
                                      @RequestParam("filter") String filter) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MeFilterTodoRequest request = objectMapper.readValue(filter, MeFilterTodoRequest.class);
        return new ResponseObject(meTodoService.checkTodoFilter(request, idPeriod, idTodoList, idTodo));
    }

    @GetMapping("/{id}")
    public ResponseObject findById(@PathVariable("id") String id) {
        return new ResponseObject(meTodoService.findById(id));
    }

    @GetMapping("/get-all-detail/{id}")
    public ResponseObject getAllDetailTodo(@PathVariable("id") String id) {
        return new ResponseObject(meTodoService.getAllDetailTodo(id));
    }

    @GetMapping("/detail/{id}")
    public ResponseObject getAllTodoByIdPeriodAndStatusTodo(@PathVariable("id") String id) {
        return new ResponseObject(meTodoService.getDetailTodo(id));
    }

    @MessageMapping("/update-priority-todo/{projectId}/{periodId}")
    @SendTo("/portal-projects/todo/{projectId}/{periodId}")
    public ResponseObject updatePriorityLevel(@RequestBody MeUpdateTodoRequest request,
                                              @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                              StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meTodoService.updatePriorityLevel(request, headerAccessor));
    }

    @MessageMapping("/update-progress-todo/{projectId}/{periodId}")
    @SendTo("/portal-projects/update-progress-todo/{projectId}/{periodId}")
    public ResponseObject updateProgress(@RequestBody MeUpdateProgressTodoRequest request,
                                         @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                         StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meTodoService.updateProgress(request, headerAccessor));
    }

    @MessageMapping("/create-todo-checklist/{projectId}/{periodId}")
    @SendTo("/portal-projects/create-todo-checklist/{projectId}/{periodId}")
    public ResponseObject createTodoChecklist(@RequestBody MeCreateDetailTodoRequest request,
                                              @ModelAttribute DesVarProjectIdAndPeriodIdRequest des) {
        return new ResponseObject(meTodoService.createTodoChecklist(request));
    }

    @MessageMapping("/update-todo-checklist/{projectId}/{periodId}")
    @SendTo("/portal-projects/update-todo-checklist/{projectId}/{periodId}")
    public ResponseObject updateTodoChecklist(@RequestBody MeUpdateDeTailTodoRequest request,
                                              @ModelAttribute DesVarProjectIdAndPeriodIdRequest des) {
        return new ResponseObject(meTodoService.updateTodoChecklist(request));
    }

    @MessageMapping("/update-statustodo-todo-checklist/{projectId}/{periodId}")
    @SendTo("/portal-projects/update-statustodo-todo-checklist/{projectId}/{periodId}")
    public ResponseObject updateStatusTodoTodoChecklist(@RequestBody MeUpdateStatusTodoRequest request,
                                                        @ModelAttribute DesVarProjectIdAndPeriodIdRequest des) {
        return new ResponseObject(meTodoService.updateStatusTodo(request));
    }

    @MessageMapping("/delete-todo-checklist/{projectId}/{periodId}")
    @SendTo("/portal-projects/delete-todo-checklist/{projectId}/{periodId}")
    public ResponseObject deleteTodoChecklist(@RequestBody MeDeleteDetailTodoRequest request,
                                              @ModelAttribute DesVarProjectIdAndPeriodIdRequest des) {
        return new ResponseObject(meTodoService.deleteDetailTodo(request));
    }

    @MessageMapping("/update-descriptions-todo/{projectId}/{periodId}")
    @SendTo("/portal-projects/update-descriptions-todo/{projectId}/{periodId}")
    public ResponseObject updateDescriptionsTodo(@RequestBody MeUpdateDescriptionsTodoRequest request,
                                                 @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                                 StompHeaderAccessor headerAccessor
    ) {
        return new ResponseObject(meTodoService.updateDescriptionsTodo(request, headerAccessor));
    }

    @MessageMapping("/update-deadline-todo/{projectId}/{periodId}")
    @SendTo("/portal-projects/update-deadline-todo/{projectId}/{periodId}")
    public ResponseObject updateDeadlineTodo(@RequestBody MeUpdateDeadlineTodoRequest request,
                                             @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                             StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meTodoService.updateDeadlineTodo(request, headerAccessor));
    }

    @MessageMapping("/delete-deadline-todo/{projectId}/{periodId}")
    @SendTo("/portal-projects/delete-deadline-todo/{projectId}/{periodId}")
    public ResponseObject deleteDeadlineTodo(@RequestBody MeDeleteDeadlineTodoRequest request,
                                             @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                             StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meTodoService.deleteDeadlineTodo(request, headerAccessor));
    }

    @MessageMapping("/create-todo/{projectId}/{periodId}")
    @SendTo("/portal-projects/create-todo/{projectId}/{periodId}")
    public ResponseObject createTodo(@RequestBody MeCreateTodoRequest request,
                                     @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                     StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meTodoService.createTodo(request, headerAccessor));
    }

    @MessageMapping("/update-index-todo/{projectId}/{periodId}")
    @SendTo("/portal-projects/update-index-todo/{projectId}/{periodId}")
    public ResponseObject updateIndexTodo(@RequestBody MeUpdateIndexTodoRequest request,
                                          @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                          StompHeaderAccessor headerAccessor
    ) {
        return new ResponseObject(meTodoService.updateIndexTodo(request, headerAccessor));
    }

    @MessageMapping("/update-index-todo-view-table/{projectId}/{periodId}")
    @SendTo("/portal-projects/update-index-todo-view-table/{projectId}/{periodId}")
    public ResponseObject updateIndexTodoViewTable(@RequestBody MeUpdateIndexTodoRequest request,
                                                   @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                                   StompHeaderAccessor headerAccessor
    ) {
        return new ResponseObject(meTodoService.updateIndexTodoViewTable(request, headerAccessor));
    }

    @MessageMapping("/update-name-todo/{projectId}/{periodId}")
    @SendTo("/portal-projects/update-name-todo/{projectId}/{periodId}")
    public ResponseObject updateNameTodo(@RequestBody MeUpdateNameTodoRequest request,
                                         @ModelAttribute DesVarProjectIdAndPeriodIdRequest des
    ) {
        return new ResponseObject(meTodoService.updateNameTodo(request));
    }

    @MessageMapping("/update-complete-todo/{projectId}/{periodId}")
    @SendTo("/portal-projects/update-complete-todo/{projectId}/{periodId}")
    public ResponseObject updateCompleteTodo(@RequestBody MeUpdateCompleteTodoRequest request,
                                             @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                             StompHeaderAccessor headerAccessor
    ) {
        return new ResponseObject(meTodoService.updateCompleteTodo(request, headerAccessor));
    }

    @MessageMapping("/delete-todo/{projectId}/{periodId}")
    @SendTo("/portal-projects/delete-todo/{projectId}/{periodId}")
    public ResponseObject deleteTodo(@RequestBody MeDeleteTodoRequest request,
                                     @ModelAttribute DesVarProjectIdAndPeriodIdRequest des
    ) {
        return new ResponseObject(meTodoService.deleteTodo(request));
    }

    @MessageMapping("/sort-todo-priority/{projectId}/{periodId}")
    @SendTo("/portal-projects/sort-todo-priority/{projectId}/{periodId}")
    public ResponseObject sortTodoPriority(@RequestBody MeSortTodoRequest request,
                                           @ModelAttribute DesVarProjectIdAndPeriodIdRequest des
    ) {
        return new ResponseObject(meTodoService.sortTodoPriority(request));
    }

    @MessageMapping("/sort-todo-deadline/{projectId}/{periodId}")
    @SendTo("/portal-projects/sort-todo-deadline/{projectId}/{periodId}")
    public ResponseObject sortTodoDeadline(@RequestBody MeSortTodoRequest request,
                                           @ModelAttribute DesVarProjectIdAndPeriodIdRequest des
    ) {
        return new ResponseObject(meTodoService.sortTodoDeadline(request));
    }

    @MessageMapping("/sort-todo-created-date/{projectId}/{periodId}")
    @SendTo("/portal-projects/sort-todo-created-date/{projectId}/{periodId}")
    public ResponseObject sortTodoCreatedDate(@RequestBody MeSortTodoRequest request,
                                              @ModelAttribute DesVarProjectIdAndPeriodIdRequest des
    ) {
        return new ResponseObject(meTodoService.sortTodoCreatedDate(request));
    }

    @MessageMapping("/sort-todo-progress/{projectId}/{periodId}")
    @SendTo("/portal-projects/sort-todo-progress/{projectId}/{periodId}")
    public ResponseObject sortTodoProgress(@RequestBody MeSortTodoRequest request,
                                           @ModelAttribute DesVarProjectIdAndPeriodIdRequest des
    ) {
        return new ResponseObject(meTodoService.sortTodoProgress(request));
    }

    @MessageMapping("/sort-todo-name/{projectId}/{periodId}")
    @SendTo("/portal-projects/sort-todo-name/{projectId}/{periodId}")
    public ResponseObject sortTodoName(@RequestBody MeSortTodoRequest request,
                                       @ModelAttribute DesVarProjectIdAndPeriodIdRequest des
    ) {
        return new ResponseObject(meTodoService.sortTodoName(request));
    }

    @MessageMapping("/update-type-todo/{projectId}/{periodId}")
    @SendTo("/portal-projects/update-type-todo/{projectId}/{periodId}")
    public ResponseObject sortTodoDeadline(@RequestBody MeUpdateTypeTodoRequest request,
                                           @ModelAttribute DesVarProjectIdAndPeriodIdRequest des
    ) {
        return new ResponseObject(meTodoService.updateTypeTodo(request));
    }

    @GetMapping("/dashboard-all")
    public ResponseObject dashboardAll(@RequestParam("projectId") String projectId, @RequestParam("periodId") String periodId) {
        return new ResponseObject(meTodoService.dashboardAll(projectId, periodId));
    }

}
