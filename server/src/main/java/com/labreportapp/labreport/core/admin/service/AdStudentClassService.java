package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.response.AdStudentCallApiRespone;

import java.util.List;

public interface AdStudentClassService {

    List<AdStudentCallApiRespone> findStudentClassByIdClass(String idClass);
}
