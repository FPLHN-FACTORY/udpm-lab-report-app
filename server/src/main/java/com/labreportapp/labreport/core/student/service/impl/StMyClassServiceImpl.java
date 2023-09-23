package com.labreportapp.labreport.core.student.service.impl;

import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.core.student.model.request.StClassRequest;
import com.labreportapp.labreport.core.student.model.request.StFindClassRequest;
import com.labreportapp.labreport.core.student.model.response.StClassResponse;
import com.labreportapp.labreport.core.student.model.response.StMyClassResponse;
import com.labreportapp.labreport.core.student.repository.StClassRepository;
import com.labreportapp.labreport.core.student.repository.StMyClassRepository;
import com.labreportapp.labreport.core.student.repository.StStudentClassesRepository;
import com.labreportapp.labreport.core.student.service.StMyClassService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.repository.LevelRepository;
import com.labreportapp.labreport.repository.SemesterRepository;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
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

  @Autowired
  @Qualifier(LevelRepository.NAME)
  private LevelRepository levelRepository;

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

    if (findStudentInClass.isPresent()) {
      Optional<StClassResponse> conditionClass = stClassRepository.checkConditionCouldJoinOrLeaveClass(req);
      if (!conditionClass.isPresent()) {
        throw new RestApiException(Message.YOU_DONT_LEAVE_CLASS);
      } else {
        stStudentClassesRepository.delete(findStudentInClass.get());
        findCurrentMyClass.get().setClassSize(findCurrentMyClass.get().getClassSize() - 1);
        stClassRepository.save(findCurrentMyClass.get());
      }
    }
  }

  @Override
  public List<SimpleEntityProjection> getAllSimpleEntityProj() {
    return levelRepository.getAllSimpleEntityProjection();
  }
}
