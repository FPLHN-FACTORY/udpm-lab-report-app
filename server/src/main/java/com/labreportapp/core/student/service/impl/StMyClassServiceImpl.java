package com.labreportapp.core.student.service.impl;

import com.labreportapp.core.common.base.SimpleEntityProjection;
import com.labreportapp.core.student.model.request.StFindClassRequest;
import com.labreportapp.core.student.model.response.StMyClassResponse;
import com.labreportapp.core.student.repository.StMyClassRepository;
import com.labreportapp.core.student.service.StMyClassService;
import com.labreportapp.entity.Semester;
import com.labreportapp.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thangncph26123
 */
@Service
public class StMyClassServiceImpl implements StMyClassService {

    @Autowired
    private StMyClassRepository stMyClassRepository;

    @Autowired
    @Qualifier(SemesterRepository.NAME)
    private SemesterRepository semesterRepository;

    @Override
    public List<StMyClassResponse> getAllClass(final StFindClassRequest req) {
        if (req.getSemesterId().equals("")) {
            List<SimpleEntityProjection> listSemester = semesterRepository.getAllSimpleEntityProjection();
            req.setSemesterId(listSemester.get(0).getId());
        }
        return stMyClassRepository.getAllClass(req);
    }
}
