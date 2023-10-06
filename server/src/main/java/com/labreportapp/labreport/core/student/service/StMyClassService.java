package com.labreportapp.labreport.core.student.service;

import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.student.model.request.StClassRequest;
import com.labreportapp.labreport.core.student.model.request.StFindClassRequest;
import com.labreportapp.labreport.core.student.model.response.StMyClassCustom;
import com.labreportapp.labreport.core.student.model.response.StMyClassResponse;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface StMyClassService {

    List<StMyClassCustom> getAllClass(final StFindClassRequest req);

    List<SimpleResponse> getAllStudentClasses(String idClass);

    void leaveClass(final StClassRequest req);

    List<SimpleEntityProjection> getAllSimpleEntityProj();
}
