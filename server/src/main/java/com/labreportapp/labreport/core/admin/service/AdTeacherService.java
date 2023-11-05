package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.common.response.SimpleResponse;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface AdTeacherService {

    List<SimpleResponse> getAllTeacher();

    List<SimpleResponse> getAllTeacherDashBoard(String name);
}
