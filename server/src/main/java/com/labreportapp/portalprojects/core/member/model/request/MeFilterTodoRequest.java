package com.labreportapp.portalprojects.core.member.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@ToString
public class MeFilterTodoRequest {

    private String idPeriod;

    private String projectId;

    private String idTodoList;

    private String name;

    private List<String> member;

    private List<String> label;

    private List<String> dueDate;

}
