package com.labreportapp.labreport.core.student.service;

import com.labreportapp.labreport.core.student.model.request.StCheckFeedBackRequest;
import com.labreportapp.labreport.core.student.model.request.StFindClassRequest;
import com.labreportapp.labreport.core.student.model.request.StStudentListFeedBackRequest;
import com.labreportapp.labreport.core.student.model.response.StMyClassCustom;
import com.labreportapp.labreport.entity.Semester;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface StFeedBackService {

    Boolean checkFeedBack(final StCheckFeedBackRequest request);

    Boolean createFeedBack(@Valid StStudentListFeedBackRequest request);

    Semester getSemesterCurrent();

    List<StMyClassCustom> getAllClass(final StFindClassRequest req);
}
