package com.labreportapp.core.student.service;

import com.labreportapp.core.student.model.response.StPointCustomResponse;
import com.labreportapp.core.student.model.response.StPointDetailRespone;

import java.util.List;

public interface StPointDetailService {

    List<StPointCustomResponse> getMyPointClass(String idClass, String studentId);
}
