package com.labreportapp.portalprojects.core.member.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MeConvertTodoResponse {

    private String id;

    private String code;

    private String name;

    private String priorityLevel;

    private String descriptions;

    private Long deadline;

    private Long completionTime;

    private Short indexTodo;

    private Short progress;

    private String imageId;

    private String nameFile;

    private Short numberTodoComplete;

    private Short numberTodo;

    private Integer progressOfTodo;

    private String deadlineString;

    private String todoListId;

    private List<String> listMemberByIdTodo;

    private List<MeConvertLabelResponse> labels;

    private Integer numberCommnets;

    private Integer numberAttachments;

    private MeListResponse list;
}
