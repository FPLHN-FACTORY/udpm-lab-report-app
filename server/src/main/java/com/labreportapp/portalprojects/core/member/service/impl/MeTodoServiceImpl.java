package com.labreportapp.portalprojects.core.member.service.impl;

import com.labreportapp.portalprojects.core.common.base.PageableObject;
import com.labreportapp.portalprojects.core.common.base.TodoAndTodoListObject;
import com.labreportapp.portalprojects.core.common.base.TodoObject;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateDetailTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeDeleteDeadlineTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeDeleteDetailTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeDeleteTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeFilterTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeFindActivityRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeFindCommentRequest;
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
import com.labreportapp.portalprojects.core.member.model.response.MeAllDetailTodo;
import com.labreportapp.portalprojects.core.member.model.response.MeBoardResponse;
import com.labreportapp.portalprojects.core.member.model.response.MeConvertLabelResponse;
import com.labreportapp.portalprojects.core.member.model.response.MeConvertTodoResponse;
import com.labreportapp.portalprojects.core.member.model.response.MeCountTodoResponse;
import com.labreportapp.portalprojects.core.member.model.response.MeDashboardAllCustom;
import com.labreportapp.portalprojects.core.member.model.response.MeDashboardItemCustom;
import com.labreportapp.portalprojects.core.member.model.response.MeDataDashboardLabelResponse;
import com.labreportapp.portalprojects.core.member.model.response.MeDataDashboardMemberResponse;
import com.labreportapp.portalprojects.core.member.model.response.MeDataDashboardTodoListResoonse;
import com.labreportapp.portalprojects.core.member.model.response.MeDeleteTodoResponse;
import com.labreportapp.portalprojects.core.member.model.response.MeDetailTodoResponse;
import com.labreportapp.portalprojects.core.member.model.response.MeLabelResponse;
import com.labreportapp.portalprojects.core.member.model.response.MeTodoResponse;
import com.labreportapp.portalprojects.core.member.repository.MeActivityRepository;
import com.labreportapp.portalprojects.core.member.repository.MeAssignRepository;
import com.labreportapp.portalprojects.core.member.repository.MeCommentRepository;
import com.labreportapp.portalprojects.core.member.repository.MeImageRepository;
import com.labreportapp.portalprojects.core.member.repository.MeLabelRepository;
import com.labreportapp.portalprojects.core.member.repository.MePeriodRepository;
import com.labreportapp.portalprojects.core.member.repository.MeProjectRepository;
import com.labreportapp.portalprojects.core.member.repository.MeReourceRepository;
import com.labreportapp.portalprojects.core.member.repository.MeTodoListRepository;
import com.labreportapp.portalprojects.core.member.repository.MeTodoRepository;
import com.labreportapp.portalprojects.core.member.service.MeTodoService;
import com.labreportapp.portalprojects.entity.ActivityTodo;
import com.labreportapp.portalprojects.entity.Period;
import com.labreportapp.portalprojects.entity.PeriodTodo;
import com.labreportapp.portalprojects.entity.Project;
import com.labreportapp.portalprojects.entity.Todo;
import com.labreportapp.portalprojects.entity.TodoList;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.constant.PriorityLevel;
import com.labreportapp.portalprojects.infrastructure.constant.StatusReminder;
import com.labreportapp.portalprojects.infrastructure.constant.StatusTodo;
import com.labreportapp.portalprojects.infrastructure.constant.TypeTodo;
import com.labreportapp.portalprojects.infrastructure.exception.rest.MessageHandlingException;
import com.labreportapp.portalprojects.infrastructure.successnotification.ConstantMessageSuccess;
import com.labreportapp.portalprojects.infrastructure.successnotification.SuccessNotificationSender;
import com.labreportapp.portalprojects.infrastructure.wsconfigure.WebSocketSessionManager;
import com.labreportapp.portalprojects.repository.PeriodTodoRepository;
import com.labreportapp.portalprojects.util.DateConverter;
import com.labreportapp.portalprojects.util.DateTimeUtil;
import com.labreportapp.portalprojects.util.TodoHelper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class MeTodoServiceImpl implements MeTodoService {

    @Autowired
    private MeTodoRepository meTodoRepository;

    @Autowired
    private PeriodTodoRepository periodTodoRepository;

    @Autowired
    private SuccessNotificationSender successNotificationSender;

    @Autowired
    private MeActivityRepository meActivityRepository;

    @Autowired
    private MePeriodRepository mePeriodRepository;

    @Autowired
    private MeProjectRepository meProjectRepository;

    @Autowired
    private MeAssignRepository meAssignRepository;

    @Autowired
    private MeLabelRepository meLabelRepository;

    @Autowired
    private MeTodoListRepository meTodoListRepository;

    @Autowired
    private MeReourceRepository meReourceRepository;

    @Autowired
    private MeImageRepository meImageRepository;

    @Autowired
    private MeCommentRepository meCommentRepository;

    @Autowired
    private TodoHelper todoHelper;

    @Autowired
    private WebSocketSessionManager webSocketSessionManager;

    @Override
    @Cacheable(value = "todosByPeriodAndTodoList", key = "#request.name.toString() + '-' " +
            "+ #request.member.toString() + '_' + #request.label.toString() + '_' " +
            "+ #request.dueDate.toString() + '_' + #request.idPeriod + '_' " +
            "+ #request.idTodoList + '_' + #request.projectId")
    public List<MeBoardResponse> getBoard(final MeFilterTodoRequest request) {
        List<TodoList> listTodoList = meTodoListRepository.findAllByProjectIdAndOrderByIndexTodoList(request.getProjectId());
        List<MeBoardResponse> listBoard = new ArrayList<>();
        for (TodoList td : listTodoList) {
            MeBoardResponse meBoardResponse = new MeBoardResponse();
            meBoardResponse.setId(td.getId());
            meBoardResponse.setCode(td.getCode());
            meBoardResponse.setIndexTodoList(td.getIndexTodoList());
            meBoardResponse.setName(td.getName());
            if (request.getIdPeriod() != null && request.getIdPeriod().equals("undefined")) {
                meBoardResponse.setTasks(new ArrayList<>());
            } else {
                request.setIdTodoList(td.getId());
                if (request.getLabel().isEmpty()) {
                    List<String> newList = new ArrayList<>();
                    newList.add("empty");
                    request.setLabel(newList);
                }
                if (request.getMember().isEmpty()) {
                    List<String> newList = new ArrayList<>();
                    newList.add("empty");
                    request.setMember(newList);
                }
                if (request.getDueDate().isEmpty()) {
                    List<String> newList = new ArrayList<>();
                    newList.add("empty");
                    request.setDueDate(newList);
                }
                List<MeTodoResponse> listMeTodoResponse = meTodoRepository.getToDoByPeriodAndIdTodoList(request);
                meBoardResponse.setTasks(convertTodoResponses(listMeTodoResponse));
            }
            listBoard.add(meBoardResponse);
        }
        return listBoard;
    }

    public List<MeConvertTodoResponse> convertTodoResponses(List<MeTodoResponse> listMeTodoResponse) {
        List<MeConvertTodoResponse> listMeConvertTodoResponse = new ArrayList<>();
        for (MeTodoResponse td : listMeTodoResponse) {
            MeConvertTodoResponse meConvertTodoResponse = new MeConvertTodoResponse();
            meConvertTodoResponse.setId(td.getId());
            meConvertTodoResponse.setCode(td.getCode());
            meConvertTodoResponse.setName(td.getName());
            meConvertTodoResponse.setPriorityLevel(td.getPriorityLevel());
            meConvertTodoResponse.setDescriptions(td.getDescriptions());
            meConvertTodoResponse.setDeadline(td.getDeadline());
            meConvertTodoResponse.setCompletionTime(td.getCompletionTime());
            meConvertTodoResponse.setIndexTodo(td.getIndexTodo());
            meConvertTodoResponse.setProgress(td.getProgress());
            meConvertTodoResponse.setImageId(td.getImageId());
            meConvertTodoResponse.setNameFile(td.getNameFile());
            meConvertTodoResponse.setNumberTodoComplete(td.getNumberTodoComplete());
            meConvertTodoResponse.setNumberTodo(td.getNumberTodo());
            meConvertTodoResponse.setTodoListId(td.getTodoListId());
            if (td.getNumberTodo() != 0) {
                meConvertTodoResponse.setProgressOfTodo((td.getNumberTodoComplete() / td.getNumberTodo()) * 100);
            } else {
                meConvertTodoResponse.setProgressOfTodo((int) td.getProgress());
            }
            if (td.getDeadline() != null) {
                meConvertTodoResponse.setDeadlineString(DateConverter.convertDateToStringTodo(td.getDeadline()));
            }
            meConvertTodoResponse.setListMemberByIdTodo(meAssignRepository.getAllMemberByIdTodo(td.getId()));
            List<MeLabelResponse> listLabel = meLabelRepository.getAllLabelByIdTodo(td.getId());
            List<MeConvertLabelResponse> listMeConvertLabelResponse = new ArrayList<>();
            for (MeLabelResponse lb : listLabel) {
                MeConvertLabelResponse meConvertLabelResponse = new MeConvertLabelResponse();
                meConvertLabelResponse.setId(lb.getId());
                meConvertLabelResponse.setName(lb.getName());
                meConvertLabelResponse.setColorLabel(lb.getColorLabel());
                listMeConvertLabelResponse.add(meConvertLabelResponse);
            }
            meConvertTodoResponse.setLabels(listMeConvertLabelResponse);
            meConvertTodoResponse.setNumberAttachments(meTodoRepository.countResourceByIdTodo(td.getId()));
            meConvertTodoResponse.setNumberCommnets(meTodoRepository.countCommentByIdTodo(td.getId()));
            listMeConvertTodoResponse.add(meConvertTodoResponse);
        }
        return listMeConvertTodoResponse;
    }

    @Override
    public MeAllDetailTodo getAllDetailTodo(String idTodo) {
        MeAllDetailTodo meAllDetailTodo = new MeAllDetailTodo();
        Todo todoFind = meTodoRepository.findById(idTodo).get();
        meAllDetailTodo.setId(todoFind.getId());
        meAllDetailTodo.setCode(todoFind.getCode());
        meAllDetailTodo.setName(todoFind.getName());
        meAllDetailTodo.setDescriptions(todoFind.getDescriptions());
        meAllDetailTodo.setDeadline(todoFind.getDeadline());
        meAllDetailTodo.setReminderTime(todoFind.getReminderTime());
        meAllDetailTodo.setStatusReminder(todoFind.getStatusReminder() != null ? todoFind.getStatusReminder().ordinal() : null);
        meAllDetailTodo.setCompletionTime(todoFind.getCompletionTime());
        meAllDetailTodo.setPriorityLevel(todoFind.getPriorityLevel() != null ? todoFind.getPriorityLevel().ordinal() : null);
        meAllDetailTodo.setProgress(todoFind.getProgress());
        meAllDetailTodo.setImageId(todoFind.getImageId());
        meAllDetailTodo.setNameFile(todoFind.getNameFile());
        meAllDetailTodo.setIndexTodo(todoFind.getIndexTodo());
        meAllDetailTodo.setTodoId(todoFind.getTodoId());
        meAllDetailTodo.setTodoListId(todoFind.getTodoListId());
        meAllDetailTodo.setStatusTodo(todoFind.getStatusTodo() != null ? todoFind.getStatusTodo().ordinal() : null);
        meAllDetailTodo.setType(todoFind.getType() != null ? todoFind.getType().ordinal() : null);
        meAllDetailTodo.setMembers(meAssignRepository.getAllMemberByIdTodo(todoFind.getId()));
        meAllDetailTodo.setLabels(meLabelRepository.getAllLabelByIdTodo(todoFind.getId()));
        meAllDetailTodo.setAttachments(meReourceRepository.getAll(todoFind.getId()));
        meAllDetailTodo.setImages(meImageRepository.getAllByIdTodo(todoFind.getId()));
        meAllDetailTodo.setListTodos(meTodoRepository.getDetailTodo(todoFind.getId()));

        MeFindActivityRequest request = new MeFindActivityRequest();
        request.setIdTodo(todoFind.getId());
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        meAllDetailTodo.setActivities(new PageableObject<>(meActivityRepository.getAll(pageRequest, request)));

        MeFindCommentRequest meFindCommentRequest = new MeFindCommentRequest();
        meFindCommentRequest.setIdTodo(todoFind.getId());
        PageRequest pageRequestComment = PageRequest.of(meFindCommentRequest.getPage(), 5);
        meAllDetailTodo.setComments(new PageableObject<>(meCommentRepository.getAllCommentByIdTodo(pageRequestComment, meFindCommentRequest)));
        return meAllDetailTodo;
    }

    @Override
    public MeTodoResponse findTodoById(String idTodo) {
        return meTodoRepository.findTodoById(idTodo);
    }

    @Override
    @Synchronized
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todoById", "detailTodosById", "todosByFilter"}, allEntries = true)
    @Transactional
    public TodoObject updateNameTodo(@Valid MeUpdateNameTodoRequest request) {
        Optional<Todo> todoFind = meTodoRepository.findById(request.getIdTodo());
        if (!todoFind.isPresent()) {
            throw new MessageHandlingException(Message.TO_DO_NOT_EXISTS);
        }
        todoFind.get().setName(request.getName());
        TodoObject todoObject = TodoObject.builder().data(meTodoRepository.save(todoFind.get())).idTodoList(request.getIdTodoList()).idTodo(request.getIdTodo()).build();
        return todoObject;
    }

    @Override
    @Cacheable(value = "todosByFilter", key = "#request.name.toString() + '-' + #request.member.toString() + '_' + #request.label.toString() + '_' + #request.dueDate.toString() + '_' + #idPeriod + '_' + #idTodoList")
    public List<MeConvertTodoResponse> filter(MeFilterTodoRequest request, String idPeriod, String idTodoList) {
//        if (request.getLabel().isEmpty()) {
//            List<String> newList = new ArrayList<>();
//            newList.add("empty");
//            request.setLabel(newList);
//        }
//        if (request.getMember().isEmpty()) {
//            List<String> newList = new ArrayList<>();
//            newList.add("empty");
//            request.setMember(newList);
//        }
//        if (request.getDueDate().isEmpty()) {
//            List<String> newList = new ArrayList<>();
//            newList.add("empty");
//            request.setDueDate(newList);
//        }
//        List<MeTodoResponse> listMeTodoResponse = meTodoRepository.filter(request, idPeriod, idTodoList);
//        return convertTodoResponses(listMeTodoResponse);
        return null;
    }

    @Override
    public String checkTodoFilter(MeFilterTodoRequest request, String idPeriod, String idTodoList, String idTodo) {
        if (request.getLabel().isEmpty()) {
            List<String> newList = new ArrayList<>();
            newList.add("empty");
            request.setLabel(newList);
        }
        if (request.getMember().isEmpty()) {
            List<String> newList = new ArrayList<>();
            newList.add("empty");
            request.setMember(newList);
        }
        if (request.getDueDate().isEmpty()) {
            List<String> newList = new ArrayList<>();
            newList.add("empty");
            request.setDueDate(newList);
        }
        return meTodoRepository.checkTodoFilter(request, idPeriod, idTodoList, idTodo);
    }

    @Override
    @Cacheable(value = "todoById", key = "#id + '_todo'")
    public Todo findById(String id) {
        return meTodoRepository.findById(id).get();
    }

    @Override
    @Cacheable(value = "detailTodosById", key = "#idTodo + '_detail'")
    public List<MeDetailTodoResponse> getDetailTodo(String idTodo) {
        return meTodoRepository.getDetailTodo(idTodo);
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todoById", "detailTodosById", "todosByFilter"}, allEntries = true)
    @Synchronized
    @Transactional
    public TodoObject updatePriorityLevel(@Valid MeUpdateTodoRequest request, StompHeaderAccessor headerAccessor) {
        Optional<Todo> todoFindById = meTodoRepository.findById(request.getIdTodo());
        if (!todoFindById.isPresent()) {
            throw new MessageHandlingException(Message.TO_DO_NOT_EXISTS);
        }
        PriorityLevel[] priorityLevels = PriorityLevel.values();
        todoFindById.get().setPriorityLevel(priorityLevels[request.getPriorityLevel()]);
        TodoObject todoObject = TodoObject.builder().data(meTodoRepository.save(todoFindById.get())).idTodoList(request.getIdTodoList()).idTodo(request.getIdTodo()).build();
        successNotificationSender.senderNotification(ConstantMessageSuccess.CAP_NHAT_THANH_CONG, headerAccessor);
        return todoObject;
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todoById", "detailTodosById", "todosByFilter"}, allEntries = true)
    @Synchronized
    @Transactional
    public TodoObject updateProgress(@Valid MeUpdateProgressTodoRequest request, StompHeaderAccessor headerAccessor) {
        Optional<Todo> todoFindById = meTodoRepository.findById(request.getIdTodo());
        if (!todoFindById.isPresent()) {
            throw new MessageHandlingException(Message.TO_DO_NOT_EXISTS);
        }
        todoFindById.get().setProgress(request.getProgress());
        updateProgressPeriod(request.getPeriodId());
        if (request.getProgress() == 100 && todoFindById.get().getDeadline() == null) {
            todoFindById.get().setCompletionTime(new Date().getTime());
            todoFindById.get().setStatusTodo(StatusTodo.HOAN_THANH_SOM);
        }
        if (request.getProgress() < 100) {
            todoFindById.get().setCompletionTime(null);
        }
        ActivityTodo activityTodo = new ActivityTodo();
        activityTodo.setTodoId(request.getIdTodo());
        activityTodo.setTodoListId(request.getIdTodoList());
        activityTodo.setProjectId(request.getProjectId());
        activityTodo.setContentAction("đã cập nhật phần trăm tiến độ của đầu việc thành " + request.getProgress() + "%");
        activityTodo.setMemberCreatedId(webSocketSessionManager.getSessionInfo(headerAccessor.getSessionId()).getId());
        TodoObject todoObject = TodoObject.builder()
                .data(meTodoRepository.save(todoFindById.get()))
                .dataActivity(meActivityRepository.save(activityTodo))
                .idTodoList(request.getIdTodoList())
                .idTodo(request.getIdTodo()).build();
        return todoObject;
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todoById", "detailTodosById", "todosByFilter"}, allEntries = true)
    @Synchronized
    public TodoObject createTodoChecklist(@Valid MeCreateDetailTodoRequest request) {
        Todo todo = new Todo();
        todo.setCode("todo_" + DateTimeUtil.convertDateToTimeStampSecond());
        todo.setName(request.getName());
        todo.setTodoId(request.getIdTodo());
        todo.setStatusTodo(StatusTodo.CHUA_HOAN_THANH);
        Todo todoSave = meTodoRepository.save(todo);
        MeCountTodoResponse meCountTodoResponse = updateProgress(request.getPeriodId(), request.getIdTodo());
        TodoObject todoObject = TodoObject.builder().data(todoSave).idTodoList(request.getIdTodoList()).idTodo(request.getIdTodo()).numberTodoComplete(meCountTodoResponse.getNumberTodoComplete()).numberTodo(meCountTodoResponse.getNumberTodo()).build();
        return todoObject;
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todoById", "detailTodosById", "todosByFilter"}, allEntries = true)
    @Synchronized
    @Transactional
    public Todo updateTodoChecklist(@Valid MeUpdateDeTailTodoRequest request) {
        Optional<Todo> todo = meTodoRepository.findById(request.getIdTodoCreateOrDelete());
        if (!todo.isPresent()) {
            throw new MessageHandlingException(Message.TO_DO_NOT_EXISTS);
        }
        todo.get().setName(request.getName());
        Todo todoSave = meTodoRepository.save(todo.get());
        return todoSave;
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todoById", "detailTodosById", "todosByFilter"}, allEntries = true)
    @Synchronized
    @Transactional
    public TodoObject updateStatusTodo(@Valid MeUpdateStatusTodoRequest request) {
        Optional<Todo> todoFind = meTodoRepository.findById(request.getIdTodoChange());
        if (!todoFind.isPresent()) {
            throw new MessageHandlingException(Message.TO_DO_NOT_EXISTS);
        }
        if (request.getStatusTodo() == 1) {
            todoFind.get().setStatusTodo(StatusTodo.DA_HOAN_THANH);
        } else {
            todoFind.get().setStatusTodo(StatusTodo.CHUA_HOAN_THANH);
        }
        Todo todoInCheckList = meTodoRepository.save(todoFind.get());
        MeCountTodoResponse meCountTodoResponse = updateProgress(request.getPeriodId(), request.getTodoId());
        TodoObject todoObject = TodoObject.builder().data(todoInCheckList).
                idTodoList(request.getIdTodoList()).
                dataTodoProgress(meTodoRepository.findById(todoInCheckList.getTodoId())).
                idTodo(request.getIdTodo()).
                numberTodoComplete(meCountTodoResponse.getNumberTodoComplete()).
                numberTodo(meCountTodoResponse.getNumberTodo()).build();
        return todoObject;
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todoById", "detailTodosById", "todosByFilter"}, allEntries = true)
    @Synchronized
    @Transactional
    public TodoObject deleteDetailTodo(@Valid MeDeleteDetailTodoRequest request) {
        meTodoRepository.deleteById(request.getId());
        MeCountTodoResponse meCountTodoResponse = updateProgress(request.getPeriodId(), request.getTodoId());
        TodoObject todoObject = TodoObject.builder().data(request.getId()).idTodoList(request.getIdTodoList()).idTodo(request.getIdTodo()).numberTodoComplete(meCountTodoResponse.getNumberTodoComplete()).numberTodo(meCountTodoResponse.getNumberTodo()).build();
        return todoObject;
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todoById", "detailTodosById", "todosByFilter"}, allEntries = true)
    @Synchronized
    @Transactional
    public TodoObject updateDescriptionsTodo(@Valid MeUpdateDescriptionsTodoRequest request, StompHeaderAccessor headerAccessor) {
        Optional<Todo> todoFind = meTodoRepository.findById(request.getIdTodo());
        if (!todoFind.isPresent()) {
            throw new MessageHandlingException(Message.TO_DO_NOT_EXISTS);
        }
        todoFind.get().setDescriptions(request.getDescriptions());
        if (request.getDescriptions().equals("<p><br></p>")) {
            todoFind.get().setDescriptions(null);
        }
        TodoObject todoObject = TodoObject.builder().data(meTodoRepository.save(todoFind.get())).idTodoList(request.getIdTodoList()).idTodo(request.getIdTodo()).build();
        successNotificationSender.senderNotification(ConstantMessageSuccess.CAP_NHAT_THANH_CONG, headerAccessor);
        return todoObject;
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todoById", "detailTodosById", "todosByFilter"}, allEntries = true)
    @Synchronized
    @Transactional
    public TodoObject updateDeadlineTodo(@Valid MeUpdateDeadlineTodoRequest request, StompHeaderAccessor headerAccessor) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date deadline = null;
        try {
            deadline = sdf.parse(request.getDeadline());
        } catch (ParseException e) {
            throw new MessageHandlingException(Message.INVALID_DATE);
        }
        Optional<Todo> todoFind = meTodoRepository.findById(request.getIdTodo());
        if (!todoFind.isPresent()) {
            throw new MessageHandlingException(Message.TO_DO_NOT_EXISTS);
        }
        todoFind.get().setDeadline(deadline.getTime());
        if (request.getReminder().equals("none")) {
            todoFind.get().setReminderTime(null);
            todoFind.get().setStatusReminder(null);
        } else {
            todoFind.get().setReminderTime(deadline.getTime() - Long.parseLong(request.getReminder()));
            todoFind.get().setStatusReminder(StatusReminder.CHUA_GUI);
        }
        ActivityTodo activityTodo = new ActivityTodo();
        activityTodo.setMemberCreatedId(webSocketSessionManager.getSessionInfo(headerAccessor.getSessionId()).getId());
        activityTodo.setProjectId(request.getProjectId());
        activityTodo.setTodoListId(request.getIdTodoList());
        activityTodo.setTodoId(request.getIdTodo());
        activityTodo.setContentAction("đã cập nhật ngày hạn của thẻ này thành " + DateConverter.convertDateToString(deadline.getTime()));

        TodoObject todoObject = TodoObject.builder().data(meTodoRepository.save(todoFind.get()))
                .dataActivity(meActivityRepository.save(activityTodo))
                .idTodoList(request.getIdTodoList()).idTodo(request.getIdTodo()).build();

        successNotificationSender.senderNotification(ConstantMessageSuccess.CAP_NHAT_THANH_CONG, headerAccessor);
        return todoObject;
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todoById", "detailTodosById", "todosByFilter"}, allEntries = true)
    @Synchronized
    @Transactional
    public TodoObject deleteDeadlineTodo(@Valid MeDeleteDeadlineTodoRequest request, StompHeaderAccessor headerAccessor) {
        Optional<Todo> todoFind = meTodoRepository.findById(request.getIdTodo());
        if (!todoFind.isPresent()) {
            throw new MessageHandlingException(Message.TO_DO_NOT_EXISTS);
        }
        todoFind.get().setDeadline(null);
        todoFind.get().setCompletionTime(null);
        todoFind.get().setReminderTime(null);
        todoFind.get().setStatusReminder(StatusReminder.CHUA_GUI);
        ActivityTodo activityTodo = new ActivityTodo();
        activityTodo.setMemberCreatedId(webSocketSessionManager.getSessionInfo(headerAccessor.getSessionId()).getId());
        activityTodo.setProjectId(request.getProjectId());
        activityTodo.setTodoListId(request.getIdTodoList());
        activityTodo.setTodoId(request.getIdTodo());
        activityTodo.setContentAction("đã xóa ngày hạn của thẻ này");
        TodoObject todoObject = TodoObject.builder().data(meTodoRepository.save(todoFind.get())).
                dataActivity(meActivityRepository.save(activityTodo)).
                idTodoList(request.getIdTodoList()).idTodo(request.getIdTodo()).build();
        successNotificationSender.senderNotification(ConstantMessageSuccess.XOA_THANH_CONG, headerAccessor);
        return todoObject;
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todoById", "detailTodosById", "todosByFilter"}, allEntries = true)
    @Synchronized
    public Todo createTodo(@Valid MeCreateTodoRequest request, StompHeaderAccessor headerAccessor) {
        if (request.getPeriodId() == null || request.getPeriodId().equals("")) {
            throw new MessageHandlingException(Message.CREATE_PERIOD_BEFORE_CREATE_TODO);
        }
        Todo todo = new Todo();
        todo.setCode(todoHelper.genCodeTodo(request.getTodoListId()));
        todo.setIndexTodo(todoHelper.genIndexTodo(request.getTodoListId(), request.getPeriodId()));
        todo.setName(request.getName());
        todo.setStatusTodo(StatusTodo.CHUA_HOAN_THANH);
        todo.setTodoListId(request.getTodoListId());
        todo.setProgress((short) 0);
        todo.setType(TypeTodo.CONG_VIEC);
        Todo newTodo = meTodoRepository.save(todo);

        PeriodTodo periodTodo = new PeriodTodo();
        periodTodo.setTodoId(newTodo.getId());
        periodTodo.setPeriodId(request.getPeriodId());
        periodTodoRepository.save(periodTodo);

        ActivityTodo activityTodo = new ActivityTodo();
        activityTodo.setTodoId(newTodo.getId());
        activityTodo.setTodoListId(request.getTodoListId());
        activityTodo.setProjectId(request.getProjectId());
        activityTodo.setMemberCreatedId(webSocketSessionManager.getSessionInfo(headerAccessor.getSessionId()).getId());
        activityTodo.setContentAction("đã thêm thẻ này vào " + request.getNameTodoList());
        meActivityRepository.save(activityTodo);

        updateProgressPeriod(request.getPeriodId());
        successNotificationSender.senderNotification(ConstantMessageSuccess.THEM_THANH_CONG, headerAccessor);
        return newTodo;
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todoById", "detailTodosById", "todosByFilter"}, allEntries = true)
    @Synchronized
    public TodoAndTodoListObject updateIndexTodo(@Valid MeUpdateIndexTodoRequest request, StompHeaderAccessor headerAccessor) {
        if (request.getIdTodoListOld().equals(request.getIdTodoListNew()) && request.getIndexBefore() == request.getIndexAfter()) {
            throw new MessageHandlingException(Message.ERROR_UNKNOWN);
        }
        Optional<Todo> todoFind = meTodoRepository.findById(request.getIdTodo());
        if (!todoFind.isPresent()) {
            throw new MessageHandlingException(Message.TO_DO_NOT_EXISTS);
        }
        if (request.getIdTodoListOld().equals(request.getIdTodoListNew())) {
            if (request.getIndexBefore() <= request.getIndexAfter()) {
                meTodoRepository.updateIndexTodoDecs(request.getIndexBefore(), request.getIndexAfter(), request.getPeriodId(), request.getIdTodoListOld());
            } else {
                meTodoRepository.updateIndexTodoAsc(request.getIndexBefore(), request.getIndexAfter(), request.getPeriodId(), request.getIdTodoListOld());
            }
            todoFind.get().setIndexTodo(request.getIndexAfter());
            return TodoAndTodoListObject.builder().data(meTodoRepository.save(todoFind.get())).
                    idTodoListOld(request.getIdTodoListOld()).
                    indexBefore((int) request.getIndexBefore()).
                    indexAfter(Integer.valueOf(request.getIndexAfter()))
                    .sessionId(request.getSessionId())
                    .build();
        } else {
            ActivityTodo activityTodo = new ActivityTodo();
            activityTodo.setTodoId(request.getIdTodo());
            activityTodo.setTodoListId(request.getIdTodoListNew());
            activityTodo.setProjectId(request.getProjectId());
            activityTodo.setMemberCreatedId(webSocketSessionManager.getSessionInfo(headerAccessor.getSessionId()).getId());
            activityTodo.setContentAction("đã kéo thẻ này từ " + request.getNameTodoListOld() + " tới " + request.getNameTodoListNew());
            Short countTodo = meTodoRepository.countTodoInTodoList(request.getIdTodoListNew(), request.getPeriodId());
            todoFind.get().setIndexTodo(request.getIndexAfter());
            todoFind.get().setTodoListId(request.getIdTodoListNew());
            if (countTodo == 0) {
                meTodoRepository.updateIndexTodoInTodoListOld(request.getIdTodoListOld(), request.getPeriodId(), request.getIndexBefore());
            } else {
                meTodoRepository.updateIndexTodoInTodoListOld(request.getIdTodoListOld(), request.getPeriodId(), request.getIndexBefore());
                meTodoRepository.updateIndexTodoInTodoListNew(request.getIdTodoListNew(), request.getPeriodId(), request.getIndexAfter());
            }
            Todo newTodo = meTodoRepository.save(todoFind.get());
            return TodoAndTodoListObject.builder().data(newTodo)
                    .dataActivity(meActivityRepository.save(activityTodo))
                    .idTodoListOld(request.getIdTodoListOld())
                    .indexBefore((int) request.getIndexBefore())
                    .indexAfter((int) request.getIndexAfter())
                    .sessionId(request.getSessionId())
                    .build();
        }
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todoById", "detailTodosById", "todosByFilter"}, allEntries = true)
    @Synchronized
    @Transactional
    public TodoObject updateCompleteTodo(@Valid MeUpdateCompleteTodoRequest request, StompHeaderAccessor headerAccessor) {
        Optional<Todo> todoFind = meTodoRepository.findById(request.getId());
        if (!todoFind.isPresent()) {
            throw new MessageHandlingException(Message.TO_DO_NOT_EXISTS);
        }
        ActivityTodo activityTodo = new ActivityTodo();
        activityTodo.setTodoListId(request.getIdTodoList());
        activityTodo.setTodoId(request.getIdTodo());
        activityTodo.setProjectId(request.getProjectId());
        activityTodo.setMemberCreatedId(webSocketSessionManager.getSessionInfo(headerAccessor.getSessionId()).getId());
        Short countTodo = meTodoRepository.countTodoInCheckList(request.getIdTodo());
        if (request.getStatus() == 0) {
            todoFind.get().setCompletionTime(new Date().getTime());
            activityTodo.setContentAction("đã đánh dấu hoàn thành công việc");
            if (new Date().getTime() > todoFind.get().getDeadline()) {
                todoFind.get().setStatusTodo(StatusTodo.HOAN_THANH_MUON);
            } else {
                todoFind.get().setStatusTodo(StatusTodo.HOAN_THANH_SOM);
            }
            if (countTodo == 0) {
                todoFind.get().setProgress((short) 100);
                updateProgressPeriod(request.getPeriodId());
            }
        } else {
            todoFind.get().setCompletionTime(null);
            activityTodo.setContentAction("đã đánh dấu chưa hoàn thành công việc");
            todoFind.get().setStatusTodo(StatusTodo.CHUA_HOAN_THANH);
            if (countTodo == 0) {
                todoFind.get().setProgress((short) 0);
                updateProgressPeriod(request.getPeriodId());
            }
        }
        TodoObject todoObject = TodoObject.builder().data(meTodoRepository.save(todoFind.get()))
                .dataActivity(meActivityRepository.save(activityTodo))
                .idTodoList(request.getIdTodoList()).idTodo(request.getIdTodo()).build();
        return todoObject;
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todoById", "detailTodosById", "todosByFilter"}, allEntries = true)
    @Transactional
    @Synchronized
    public TodoAndTodoListObject updateIndexTodoViewTable(@Valid MeUpdateIndexTodoRequest request, StompHeaderAccessor headerAccessor) {
        Optional<Todo> todoFind = meTodoRepository.findById(request.getIdTodo());
        if (!todoFind.isPresent()) {
            throw new MessageHandlingException(Message.TO_DO_NOT_EXISTS);
        }
        meTodoRepository.updateIndexTodoInTodoListOld(request.getIdTodoListOld(), request.getPeriodId(), request.getIndexBefore());
        meTodoRepository.updateIndexTodoInTodoListNew(request.getIdTodoListNew(), request.getPeriodId(), (short) 0);
        todoFind.get().setIndexTodo((short) 0);
        todoFind.get().setTodoListId(request.getIdTodoListNew());

        ActivityTodo activityTodo = new ActivityTodo();
        activityTodo.setTodoId(request.getIdTodo());
        activityTodo.setTodoListId(request.getIdTodoListNew());
        activityTodo.setProjectId(request.getProjectId());
        activityTodo.setMemberCreatedId(webSocketSessionManager.getSessionInfo(headerAccessor.getSessionId()).getId());
        activityTodo.setContentAction("đã kéo thẻ này từ " + request.getNameTodoListOld() + " tới " + request.getNameTodoListNew());

        TodoAndTodoListObject todoAndTodoListObject = TodoAndTodoListObject.builder().data(meTodoRepository.save(todoFind.get())).
                dataActivity(meActivityRepository.save(activityTodo)).
                idTodoListOld(request.getIdTodoListOld()).
                indexBefore((int) request.getIndexBefore()).
                indexAfter(Integer.valueOf(0)).
                sessionId(request.getSessionId()).
                build();
        return todoAndTodoListObject;
    }

    @Override
    @Synchronized
    @Transactional
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todoById", "detailTodosById", "todosByFilter"}, allEntries = true)
    public MeDeleteTodoResponse deleteTodo(@Valid MeDeleteTodoRequest request) {
        Optional<Todo> todoFind = meTodoRepository.findById(request.getId());
        if (!todoFind.isPresent()) {
            throw new MessageHandlingException(Message.TO_DO_NOT_EXISTS);
        }

        meTodoRepository.deleteCommentTodo(request.getId());
        meTodoRepository.deleteAssignTodo(request.getId());

        meTodoRepository.deleteImageTodo(request.getId());
//        Path currentPath = Paths.get("");
//        String parentPath = currentPath.toAbsolutePath().toString().substring(0, currentPath.toAbsolutePath().toString().lastIndexOf("\\"));
//        String pathFile = parentPath + "/front_end/assets/imgTodo/" + request.getId();
//        try {
//            File directory = new File(pathFile);
//            FileUtils.deleteDirectory(directory);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        meTodoRepository.deleteResourceTodo(request.getId());
        meTodoRepository.deleteActivityTodo(request.getId(), request.getIdProject());
        meTodoRepository.deletePeriodTodo(request.getId(), request.getIdPeriod());
        meTodoRepository.deleteTodoInCheckList(request.getId());
        meTodoRepository.deleteLabelProjectTodo(request.getId());

        String idTodoList = todoFind.get().getTodoListId();
        meTodoRepository.updateIndexTodo(idTodoList, (int) todoFind.get().getIndexTodo());
        meTodoRepository.deleteById(request.getId());

        updateProgressPeriod(request.getIdPeriod());
        MeDeleteTodoResponse meDeleteTodoResponse = MeDeleteTodoResponse.builder()
                .idTodo(request.getId()).idTodoList(idTodoList).build();
        return meDeleteTodoResponse;
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todosByFilter"}, allEntries = true)
    @Synchronized
    public String sortTodoPriority(@Valid MeSortTodoRequest request) {
        if (request.getType() == 0) {
            meTodoRepository.sortByPriorityAsc(request.getIdPeriod());
        }
        if (request.getType() == 1) {
            meTodoRepository.sortByPriorityDesc(request.getIdPeriod());
        }
        return request.getIdPeriod();
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todosByFilter"}, allEntries = true)
    @Synchronized
    public String sortTodoDeadline(MeSortTodoRequest request) {
        if (request.getType() == 0) {
            meTodoRepository.sortByDeadlineAsc(request.getIdPeriod());
        }
        if (request.getType() == 1) {
            meTodoRepository.sortByDeadlineDesc(request.getIdPeriod());
        }
        return request.getIdPeriod();
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todosByFilter"}, allEntries = true)
    @Synchronized
    public String sortTodoCreatedDate(MeSortTodoRequest request) {
        if (request.getType() == 0) {
            meTodoRepository.sortByCreatedDateAsc(request.getIdPeriod());
        }
        if (request.getType() == 1) {
            meTodoRepository.sortByCreatedDateDesc(request.getIdPeriod());
        }
        return request.getIdPeriod();
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todosByFilter"}, allEntries = true)
    @Synchronized
    public String sortTodoName(MeSortTodoRequest request) {
        if (request.getType() == 0) {
            meTodoRepository.sortByNameAsc(request.getIdPeriod());
        }
        if (request.getType() == 1) {
            meTodoRepository.sortByNameDesc(request.getIdPeriod());
        }
        return request.getIdPeriod();
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todosByFilter"}, allEntries = true)
    @Synchronized
    public String sortTodoProgress(MeSortTodoRequest request) {
        if (request.getType() == 0) {
            meTodoRepository.sortByProgressAsc(request.getIdPeriod());
        }
        if (request.getType() == 1) {
            meTodoRepository.sortByProgressDesc(request.getIdPeriod());
        }
        return request.getIdPeriod();
    }

    @Override
    @Synchronized
    @Transactional
    @CacheEvict(value = {"todoById", "detailTodosById"}, allEntries = true)
    public TodoObject updateTypeTodo(@Valid MeUpdateTypeTodoRequest request) {
        Optional<Todo> todoFind = meTodoRepository.findById(request.getId());
        if (!todoFind.isPresent()) {
            throw new MessageHandlingException(Message.TO_DO_NOT_EXISTS);
        }
        if (request.getType() == 0) {
            todoFind.get().setType(TypeTodo.TAI_LIEU);
        } else {
            todoFind.get().setType(TypeTodo.CONG_VIEC);
        }
        Todo newTodo = meTodoRepository.save(todoFind.get());
        updateProgressPeriod(request.getPeriodId());
        TodoObject todoObject = TodoObject.builder().data(newTodo).
                idTodoList(request.getIdTodoList()).
                idTodo(request.getIdTodo()).build();
        return todoObject;
    }

    @Override
    public MeDashboardAllCustom dashboardAll(String projectId, String periodId) {
        MeDashboardAllCustom meDashboardAllCustom = new MeDashboardAllCustom();

        List<MeDashboardItemCustom> meDashboardItemTodoLists = new ArrayList<>();
        List<MeDataDashboardTodoListResoonse> listTodoList = meTodoRepository.countTodoByTodoListPeriod(projectId, periodId);
        meDashboardItemTodoLists = listTodoList.stream()
                .map(todoList -> {
                    MeDashboardItemCustom customItem = new MeDashboardItemCustom();
                    customItem.setLabel(todoList.getName());
                    customItem.setY((long) todoList.getList());
                    return customItem;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        meDashboardAllCustom.setListDashboardTodoList(meDashboardItemTodoLists);

        List<MeDashboardItemCustom> meDashboardItemDueDates = new ArrayList<>();
        MeDashboardItemCustom meDashboardItemCustomChuaHoanThanh = new MeDashboardItemCustom();
        meDashboardItemCustomChuaHoanThanh.setLabel("Chưa hoàn thành");
        meDashboardItemCustomChuaHoanThanh.setY((long) meTodoRepository.countTodoByDueDatePeriod(projectId, periodId, 0));
        meDashboardItemDueDates.add(meDashboardItemCustomChuaHoanThanh);
        MeDashboardItemCustom meDashboardItemCustomHoanThanhSom = new MeDashboardItemCustom();
        meDashboardItemCustomHoanThanhSom.setLabel("Hoàn thành sớm");
        meDashboardItemCustomHoanThanhSom.setY((long) meTodoRepository.countTodoByDueDatePeriod(projectId, periodId, 2));
        meDashboardItemDueDates.add(meDashboardItemCustomHoanThanhSom);
        MeDashboardItemCustom meDashboardItemCustomHoanThanhMuon = new MeDashboardItemCustom();
        meDashboardItemCustomHoanThanhMuon.setLabel("Hoàn thành muộn");
        meDashboardItemCustomHoanThanhMuon.setY((long) meTodoRepository.countTodoByDueDatePeriod(projectId, periodId, 3));
        meDashboardItemDueDates.add(meDashboardItemCustomHoanThanhMuon);
        MeDashboardItemCustom meDashboardItemCustomQuaHan = new MeDashboardItemCustom();
        meDashboardItemCustomQuaHan.setLabel("Quá hạn");
        meDashboardItemCustomQuaHan.setY((long) meTodoRepository.countTodoByDueDatePeriod(projectId, periodId, 4));
        meDashboardItemDueDates.add(meDashboardItemCustomQuaHan);
        MeDashboardItemCustom meDashboardItemCustomKhongCoNgayHan = new MeDashboardItemCustom();
        meDashboardItemCustomKhongCoNgayHan.setLabel("Không có ngày hạn");
        meDashboardItemCustomKhongCoNgayHan.setY((long) meTodoRepository.countTodoByNoDueDatePeriod(projectId, periodId));
        meDashboardItemDueDates.add(meDashboardItemCustomKhongCoNgayHan);
        meDashboardAllCustom.setListDashboardDueDate(meDashboardItemDueDates);

        List<MeDashboardItemCustom> meDashboardItemMembers = new ArrayList<>();
        List<MeDataDashboardMemberResponse> listMember = meTodoRepository.countTodoByMemberPeriod(projectId, periodId);
        meDashboardItemMembers = listMember.stream()
                .map(todoList -> {
                    MeDashboardItemCustom customItem = new MeDashboardItemCustom();
                    customItem.setLabel(todoList.getName());
                    customItem.setY((long) todoList.getMember());
                    return customItem;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        MeDashboardItemCustom meDashboardItemCustomKoCoThanhVien = new MeDashboardItemCustom();
        meDashboardItemCustomKoCoThanhVien.setLabel("Thẻ không có thành viên");
        meDashboardItemCustomKoCoThanhVien.setY((long) meTodoRepository.countTodoByNoMemberPeriod(projectId, periodId));
        meDashboardItemMembers.add(meDashboardItemCustomKoCoThanhVien);
        meDashboardAllCustom.setListDashboardMember(meDashboardItemMembers);

        List<MeDashboardItemCustom> meDashboardItemLabels = new ArrayList<>();
        List<MeDataDashboardLabelResponse> listLabel = meTodoRepository.countTodoByLabelPeriod(projectId, periodId);
        meDashboardItemLabels = listLabel.stream()
                .map(todoList -> {
                    MeDashboardItemCustom customItem = new MeDashboardItemCustom();
                    customItem.setLabel(todoList.getName());
                    customItem.setY((long) todoList.getLabel());
                    return customItem;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        MeDashboardItemCustom meDashboardItemCustomKoCoNhan = new MeDashboardItemCustom();
        meDashboardItemCustomKoCoNhan.setLabel("Thẻ không có nhãn");
        meDashboardItemCustomKoCoNhan.setY((long) meTodoRepository.countTodoByNoLabelPeriod(projectId, periodId));
        meDashboardItemLabels.add(meDashboardItemCustomKoCoNhan);
        meDashboardAllCustom.setListDashboardLabel(meDashboardItemLabels);
        return meDashboardAllCustom;
    }

    @Override
    public Integer getAllTodoTypeWork(String projectId, String periodId) {
        return meTodoRepository.getAllTodoTypeWork(projectId, periodId);
    }

    @Override
    public List<Todo> getAllTodoComplete(String projectId, String periodId) {
        return meTodoRepository.getAllTodoComplete(projectId, periodId);
    }

    public MeCountTodoResponse updateProgress(String idPeriod, String todoId) {
        Short countTodoComplete = meTodoRepository.countTodoComplete(todoId);
        Short countTodoInCheckList = meTodoRepository.countTodoInCheckList(todoId);
        Optional<Todo> todo = meTodoRepository.findById(todoId);
        if (countTodoInCheckList > 0) {
            short progress = (short) (countTodoComplete * 100 / countTodoInCheckList);
            todo.get().setProgress(progress);
            if (todo.get().getDeadline() == null) {
                if (progress == 100) {
                    todo.get().setStatusTodo(StatusTodo.HOAN_THANH_SOM);
                    todo.get().setCompletionTime(new Date().getTime());
                } else {
                    todo.get().setStatusTodo(StatusTodo.CHUA_HOAN_THANH);
                    todo.get().setCompletionTime(null);
                }
            }
        }
        if (countTodoInCheckList == 0) {
            todo.get().setProgress((short) 0);
        }
        meTodoRepository.save(todo.get());
        updateProgressPeriod(idPeriod);
        return new MeCountTodoResponse(countTodoComplete, countTodoInCheckList);
    }

    private void updateProgressPeriod(String idPeriod) {
        try {
            List<Short> listProgressByIdPeriod = meTodoRepository.getAllProgressByIdPeriod(idPeriod);

            int sum = 0;
            for (Short progress : listProgressByIdPeriod) {
                if (progress != null) {
                    sum += progress;
                }
            }
            Optional<Period> periodFind = mePeriodRepository.findById(idPeriod);
            if (!periodFind.isPresent()) {
                throw new MessageHandlingException(Message.PERIOD_NOT_EXISTS);
            }
            if (sum != 0) {
                float average = (float) sum / listProgressByIdPeriod.size();
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
                String roundedAverage = decimalFormat.format(average);

                periodFind.get().setProgress(Float.parseFloat(roundedAverage));
                mePeriodRepository.save(periodFind.get());
            } else {
                periodFind.get().setProgress(Float.parseFloat("0"));
                mePeriodRepository.save(periodFind.get());
            }

            List<Float> listProgressByIdProject = mePeriodRepository.getAllProgressByIdProject(periodFind.get().getProjectId());

            float sumPro = 0;
            for (Float progress : listProgressByIdProject) {
                sumPro += progress;
            }
            float averagePro = sumPro / listProgressByIdProject.size();

            DecimalFormat decimalFormatPro = new DecimalFormat("#.##");
            decimalFormatPro.setRoundingMode(RoundingMode.HALF_UP);
            String roundedAveragePro = decimalFormatPro.format(averagePro);

            Optional<Project> projectFind = meProjectRepository.findById(periodFind.get().getProjectId());
            if (!projectFind.isPresent()) {
                throw new MessageHandlingException(Message.PROJECT_NOT_EXISTS);
            }
            projectFind.get().setProgress(Float.parseFloat(roundedAveragePro));
            meProjectRepository.save(projectFind.get());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
