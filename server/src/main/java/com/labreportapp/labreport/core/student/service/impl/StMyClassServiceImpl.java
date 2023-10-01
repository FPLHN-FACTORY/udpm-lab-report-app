package com.labreportapp.labreport.core.student.service.impl;

import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.student.model.request.StClassRequest;
import com.labreportapp.labreport.core.student.model.request.StFindClassRequest;
import com.labreportapp.labreport.core.student.model.response.StClassResponse;
import com.labreportapp.labreport.core.student.model.response.StMyClassCustom;
import com.labreportapp.labreport.core.student.model.response.StMyClassResponse;
import com.labreportapp.labreport.core.student.repository.StClassRepository;
import com.labreportapp.labreport.core.student.repository.StMyClassRepository;
import com.labreportapp.labreport.core.student.repository.StStudentClassesRepository;
import com.labreportapp.labreport.core.student.service.StMyClassService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.repository.LevelRepository;
import com.labreportapp.labreport.repository.SemesterRepository;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Override
    public List<StMyClassCustom> getAllClass(final StFindClassRequest req) {
        List<StMyClassResponse> listMyClassResponse = stMyClassRepository.getAllClass(req);
        List<String> distinctTeacherIds = listMyClassResponse.stream()
                .map(StMyClassResponse::getTeacherId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listSimpleResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(distinctTeacherIds);
        List<StMyClassCustom> listCustom = new ArrayList<>();
        for (StMyClassResponse stMyClassResponse : listMyClassResponse) {
            StMyClassCustom stMyClassCustom = new StMyClassCustom();
            stMyClassCustom.setId(stMyClassResponse.getId());
            stMyClassCustom.setClassPeriod(stMyClassResponse.getClassPeriod());
            stMyClassCustom.setCode(stMyClassResponse.getCode());
            stMyClassCustom.setLevel(stMyClassResponse.getLevel());
            stMyClassCustom.setNameActivity(stMyClassResponse.getNameActivity());
            stMyClassCustom.setStartTime(stMyClassResponse.getStartTime());
            stMyClassCustom.setStt(stMyClassResponse.getStt());
            stMyClassCustom.setTeacherId(stMyClassResponse.getTeacherId());
            for (SimpleResponse simpleResponse : listSimpleResponse) {
                if (stMyClassResponse.getTeacherId() != null) {
                    if (stMyClassResponse.getTeacherId().equals(simpleResponse.getId())) {
                        stMyClassCustom.setUserNameTeacher(simpleResponse.getUserName());
                        stMyClassCustom.setNameTeacher(simpleResponse.getName());
                        break;
                    }
                }
            }
            listCustom.add(stMyClassCustom);
        }
        return listCustom;
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
