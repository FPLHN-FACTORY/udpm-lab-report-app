package com.labreportapp.portalprojects.core.member.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class MeDashboardAllCustom {

    private List<MeDashboardItemCustom> listDashboardTodoList;

    private List<MeDashboardItemCustom> listDashboardDueDate;

    private List<MeDashboardItemCustom> listDashboardMember;

    private List<MeDashboardItemCustom> listDashboardLabel;
}
