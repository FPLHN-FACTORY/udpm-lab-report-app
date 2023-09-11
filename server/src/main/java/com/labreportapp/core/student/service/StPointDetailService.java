package com.labreportapp.core.student.service;

import com.labreportapp.core.student.model.response.StPointDetailRespone;

import java.util.List;

public interface StPointDetailService {

    StPointDetailRespone getMyPointClass(String idClass, String studentId);
}
