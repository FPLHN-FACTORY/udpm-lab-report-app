package com.labreportapp.core.admin.service;

import com.labreportapp.core.admin.model.response.AdStudentCallApiRespone;

import java.util.List;

public interface AdStudentClassService {

    List<AdStudentCallApiRespone> findStudentClassByIdClass(String idClass);
}
