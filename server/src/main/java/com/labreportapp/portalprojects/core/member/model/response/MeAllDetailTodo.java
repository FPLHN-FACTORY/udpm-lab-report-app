package com.labreportapp.portalprojects.core.member.model.response;

import com.labreportapp.portalprojects.core.common.base.PageableObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeAllDetailTodo {

    private String id;
    private String code;
    private String name;
    private String descriptions;
    private Long deadline;
    private Long reminderTime;
    private Integer statusReminder;
    private Long completionTime;
    private Integer priorityLevel;
    private Short progress;
    private String imageId;
    private String nameFile;
    private Short indexTodo;
    private String todoId;
    private String todoListId;
    private Integer statusTodo;
    private Integer type;
    private List<String> members;
    private List<MeLabelResponse> labels;
    private List<MeResourceResponse> attachments;
    private List<MeImageResponse> images;
    private PageableObject<MeActivityResponse> activities;
    private List<MeDetailTodoResponse> listTodos;
    private PageableObject<MeCommentResponse> comments;
}
