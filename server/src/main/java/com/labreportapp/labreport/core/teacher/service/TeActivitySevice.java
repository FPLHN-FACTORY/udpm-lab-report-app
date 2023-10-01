package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeActivityResponse;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeActivitySevice {

    List<TeActivityResponse> getAllByIdSemester(final TeFindClassRequest teFindClass);

}
