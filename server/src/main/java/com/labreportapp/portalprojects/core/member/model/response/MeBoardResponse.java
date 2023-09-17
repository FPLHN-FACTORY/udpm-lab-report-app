package com.labreportapp.portalprojects.core.member.model.response;

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
public class MeBoardResponse {

    private String id;

    private String code;

    private String name;

    private Byte indexTodoList;

    private List<MeConvertTodoResponse> tasks;
}
