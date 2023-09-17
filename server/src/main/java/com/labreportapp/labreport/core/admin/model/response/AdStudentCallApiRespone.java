package com.labreportapp.labreport.core.admin.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdStudentCallApiRespone {
    private String id;

    private String name;

    private String username;

    private String email;

    private String idStudent;

    private String idStudentClass;

    private String role;

    private String statusStudent;

    private String idTeam;

    private String codeTeam;

    private String nameTeam;
}
