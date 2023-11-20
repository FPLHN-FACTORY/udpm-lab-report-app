package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.base.CategoryHoneyResponse;
import com.labreportapp.labreport.core.common.base.HoneyConversionRequest;
import com.labreportapp.labreport.core.common.base.ItemStudentHoneyRequest;
import com.labreportapp.labreport.core.teacher.model.response.TePointCustomResponse;
import com.labreportapp.labreport.core.teacher.repository.TePointRepository;
import com.labreportapp.labreport.core.teacher.service.TeAddHoneyService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.ClassConfiguration;
import com.labreportapp.labreport.infrastructure.constant.StatusHoneyPlus;
import com.labreportapp.labreport.repository.ClassConfigurationRepository;
import com.labreportapp.labreport.repository.ClassRepository;
import com.labreportapp.labreport.util.CallApiHoney;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.CustomException;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author todo thangncph26123
 */
@Service
@Validated
public class TeAddHoneyServiceImpl implements TeAddHoneyService {

    @Autowired
    private CallApiHoney callApiHoney;

    @Autowired
    private TePointRepository tePointRepository;

    @Autowired
    @Qualifier(ClassRepository.NAME)
    private ClassRepository classRepository;

    @Autowired
    @Qualifier(ClassConfigurationRepository.NAME)
    private ClassConfigurationRepository classConfigurationRepository;

    @Override
    public List<CategoryHoneyResponse> getAllCategory() {
        return callApiHoney.getAllListCategory();
    }

    @Override
    public Boolean addHoney(String idClass, String categoryId) {
        Optional<Class> classFind = classRepository.findById(idClass);
        if (!classFind.isPresent()) {
            throw new CustomException(Message.CLASS_NOT_EXISTS);
        }
        if (classFind.get().getStatusHoneyPlus() == StatusHoneyPlus.DA_CONG) {
            throw new RestApiException(Message.LOP_NAY_DA_QUY_DOI_MAT_ONG);
        }
        List<TePointCustomResponse> listResponse = tePointRepository.getAllPointCustomByIdClass(idClass);
        List<ClassConfiguration> classConfigurationList = classConfigurationRepository.findAll();
        if (classConfigurationList.isEmpty()) {
            throw new RestApiException(Message.BAN_CHUA_TAO_CAU_HINH);
        }
        List<ItemStudentHoneyRequest> listStudent = new ArrayList<>();
        for (TePointCustomResponse tePointCustomResponse : listResponse) {
            ItemStudentHoneyRequest itemStudentHoneyRequest = new ItemStudentHoneyRequest();
            itemStudentHoneyRequest.setId(tePointCustomResponse.getIdStudent());
            itemStudentHoneyRequest.setEmail(tePointCustomResponse.getEmail());
            itemStudentHoneyRequest.setNumberHoney((int) (tePointCustomResponse.getFinalPoint() * classConfigurationList.get(0).getNumberHoney()));
            listStudent.add(itemStudentHoneyRequest);
        }
        HoneyConversionRequest request = new HoneyConversionRequest();
        request.setCode("MODULE_LAB_REPORT_APP");
        request.setListStudent(listStudent);
        request.setCategoryId(categoryId);
        System.out.println(request.toString() + " aaaaaaaaaaaaaaa");
        Boolean check = callApiHoney.addPointStudentLabReportApp(request);
        classFind.get().setStatusHoneyPlus(StatusHoneyPlus.DA_CONG);
        classRepository.save(classFind.get());
        return check;
    }
}
