package com.labreportapp.labreport.core.student.service;

import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.entity.Semester;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface StSemesterService {

    List<Semester> getAllSemester();
}
