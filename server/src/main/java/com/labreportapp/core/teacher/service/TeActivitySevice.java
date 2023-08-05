package com.labreportapp.core.teacher.service;

import com.labreportapp.core.teacher.model.request.TeFindClass;
import com.labreportapp.core.teacher.model.response.TeActivityRespone;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeActivitySevice {

    List<TeActivityRespone> getAllByIdSemester(final TeFindClass teFindClass);

}
