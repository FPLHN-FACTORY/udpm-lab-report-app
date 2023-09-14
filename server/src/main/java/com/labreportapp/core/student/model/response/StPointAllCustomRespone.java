package com.labreportapp.core.student.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StPointAllCustomRespone {
    private String id;

    private String classCode;

    private List<StPointCustomResponse> points;

}
