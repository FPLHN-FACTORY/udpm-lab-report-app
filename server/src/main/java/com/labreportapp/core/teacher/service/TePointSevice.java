package com.labreportapp.core.teacher.service;

import com.labreportapp.core.teacher.model.response.TePointRespone;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TePointSevice {

    List<TePointRespone> getPointStudentById(String idClass);

}
