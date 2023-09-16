package com.labreportapp.core.student.service.impl;

import com.labreportapp.core.common.base.SimpleEntityProjection;
import com.labreportapp.core.student.model.request.StClassRequest;
import com.labreportapp.core.student.model.request.StFindClassRequest;
import com.labreportapp.core.student.model.response.StClassResponse;
import com.labreportapp.core.student.model.response.StMyClassResponse;
import com.labreportapp.core.student.repository.StClassRepository;
import com.labreportapp.core.student.repository.StMyClassRepository;
import com.labreportapp.core.student.repository.StStudentClassesRepository;
import com.labreportapp.core.student.service.StMyClassService;
import com.labreportapp.entity.Class;
import com.labreportapp.entity.StudentClasses;
import com.labreportapp.infrastructure.constant.Message;
import com.labreportapp.infrastructure.exception.rest.RestApiException;
import com.labreportapp.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author thangncph26123
 */
@Service
public class StMyClassServiceImpl implements StMyClassService {

    @Autowired
    private StMyClassRepository stMyClassRepository;

    @Autowired
    private StClassRepository stClassRepository;

    @Autowired
    private StStudentClassesRepository stStudentClassesRepository;

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

    @Override
    public void leaveClass(final StClassRequest req) {
        Optional<Class> findCurrentMyClass = stClassRepository.findById(req.getIdClass());
        Optional<StudentClasses> findStudentInClass = stStudentClassesRepository.
                findStudentClassesByClassIdAndStudentId(req.getIdClass(), req.getIdStudent());
        StClassResponse checkTheFirstMeetingDate = stMyClassRepository.checkTheFirstMeetingDateByClass(req);

        if (findStudentInClass.isPresent()) {
            if (checkTheFirstMeetingDate == null) {
                stStudentClassesRepository.delete(findStudentInClass.get());
                findCurrentMyClass.get().setClassSize(findCurrentMyClass.get().getClassSize() - 1);
                stClassRepository.save(findCurrentMyClass.get());
            } else {
                throw new RestApiException(Message.YOU_DONT_LEAVE_CLASS);
            }
        }
    }
}
