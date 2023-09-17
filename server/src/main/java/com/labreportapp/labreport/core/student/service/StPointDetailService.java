package com.labreportapp.labreport.core.student.service;

import com.labreportapp.labreport.core.student.model.response.StPointCustomResponse;

import java.util.List;

public interface StPointDetailService {

    List<StPointCustomResponse> getMyPointClass(String idClass, String studentId);
}
