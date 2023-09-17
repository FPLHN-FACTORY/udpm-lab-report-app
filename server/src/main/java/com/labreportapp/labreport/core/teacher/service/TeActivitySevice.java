package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeActivityRespone;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeActivitySevice {

    List<TeActivityRespone> getAllByIdSemester(final TeFindClassRequest teFindClass);

}
