package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.teacher.model.response.TeSemesterResponse;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeSemesterService {

    List<TeSemesterResponse> getAllSemester();

}
