package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdCreateClassRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindClassRequest;
import com.labreportapp.labreport.core.admin.model.response.AdActivityClassResponse;
import com.labreportapp.labreport.core.admin.model.response.AdClassCustomResponse;
import com.labreportapp.labreport.core.admin.model.response.AdClassResponse;
import com.labreportapp.labreport.core.admin.model.response.AdDetailClassRespone;
import com.labreportapp.labreport.core.admin.model.response.AdListClassCustomResponse;
import com.labreportapp.labreport.core.admin.model.response.AdSemesterAcResponse;
import com.labreportapp.labreport.core.admin.repository.AdActivityRepository;
import com.labreportapp.labreport.core.admin.repository.AdClassRepository;
import com.labreportapp.labreport.core.admin.service.AdClassService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.entity.Activity;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.infrastructure.constant.ClassPeriod;
import com.labreportapp.labreport.repository.LevelRepository;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import com.labreportapp.labreport.util.FormUtils;
import com.labreportapp.labreport.util.RandomString;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author quynhncph26201
 */
@Service
@Validated
public class AdClassManagerServiceImpl implements AdClassService {

    private FormUtils formUtils = new FormUtils();

    @Autowired
    private AdClassRepository repository;

    @Autowired
    private AdActivityRepository adActivityRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Autowired
    @Qualifier(LevelRepository.NAME)
    private LevelRepository levelRepository;

    @Override
    public List<AdClassResponse> getAllClass() {
        return repository.getAllClass();
    }

    @Override
    public List<SimpleEntityProjection> getAllLevel() {
        return levelRepository.getAllSimpleEntityProjection();
    }

    @Override
    public List<AdClassResponse> getAllClassBySemester(AdFindClassRequest adFindClass) {
        return repository.getAllClassBySemester(adFindClass);
    }

    @Override
    public List<AdClassResponse> findClassByCondition(String code, Long classPeriod, String idTeacher) {
        return repository.findClassByCondition(code, classPeriod, idTeacher);
    }

    @Override
    public List<AdSemesterAcResponse> getAllSemester() {
        return repository.getAllSemesters();
    }

    @Override
    public List<AdActivityClassResponse> getAllByIdSemester(AdFindClassRequest adFindClass) {
        return repository.getAllByIdSemester(adFindClass);
    }

    @Override
    public AdClassCustomResponse createClass(@Valid AdCreateClassRequest request) {
        Class classNew = new Class();
        String codeCheck = repository.checkCodeExist(request.getCode(), request.getActivityId());
        if (codeCheck != null) {
            throw new RestApiException(Message.CODE_CLASS_EXISTS);
        }
        classNew.setCode(request.getCode());
        classNew.setClassSize(0);
        classNew.setClassPeriod(ClassPeriod.values()[Math.toIntExact(request.getClassPeriod())]);
        classNew.setPassword(RandomString.random());
        Optional<Activity> activityFind = adActivityRepository.findById(request.getActivityId());
        if (!activityFind.isPresent()) {
            throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
        }
        classNew.setActivityId(request.getActivityId());
        classNew.setStartTime(request.getStartTime());
        if (!request.getTeacherId().equals("")) {
            classNew.setTeacherId(request.getTeacherId());
        }
        repository.save(classNew);
        AdClassCustomResponse adClassCustomResponse = new AdClassCustomResponse();
        adClassCustomResponse.setId(classNew.getId());
        adClassCustomResponse.setClassSize(classNew.getClassSize());
        adClassCustomResponse.setClassPeriod(request.getClassPeriod());
        adClassCustomResponse.setCode(classNew.getCode());
        adClassCustomResponse.setActivityName(activityFind.get().getName());
        adClassCustomResponse.setStartTime(classNew.getStartTime());
        adClassCustomResponse.setNameLevel(levelRepository.findById(activityFind.get().getLevelId()).get().getName());
        if (!request.getTeacherId().equals("")) {
            adClassCustomResponse.setTeacherId(request.getTeacherId());

            SimpleResponse response = convertRequestCallApiIdentity.handleCallApiGetUserById(request.getTeacherId());
            adClassCustomResponse.setUserNameTeacher(response.getUserName());
        }

        return adClassCustomResponse;
    }

    @Override
    public AdClassCustomResponse updateClass(AdCreateClassRequest request, String id) {
        Class classNew = repository.findById(id).get();
        classNew.setCode(request.getCode());
        classNew.setStartTime(request.getStartTime());
        classNew.setClassPeriod(ClassPeriod.values()[Math.toIntExact(request.getClassPeriod())]);
        Optional<Activity> activityFind = adActivityRepository.findById(request.getActivityId());
        if (!activityFind.isPresent()) {
            throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
        }
        classNew.setActivityId(request.getActivityId());
        if (!request.getTeacherId().equals("")) {
            classNew.setTeacherId(request.getTeacherId());
        }
        repository.save(classNew);
        AdClassCustomResponse adClassCustomResponse = new AdClassCustomResponse();
        adClassCustomResponse.setId(classNew.getId());
        adClassCustomResponse.setClassSize(classNew.getClassSize());
        adClassCustomResponse.setClassPeriod(request.getClassPeriod());
        adClassCustomResponse.setCode(classNew.getCode());
        adClassCustomResponse.setActivityName(activityFind.get().getName());
        adClassCustomResponse.setStartTime(classNew.getStartTime());
        adClassCustomResponse.setNameLevel(levelRepository.findById(activityFind.get().getLevelId()).get().getName());
        if (!request.getTeacherId().equals("")) {
            adClassCustomResponse.setTeacherId(request.getTeacherId());

            SimpleResponse response = convertRequestCallApiIdentity.handleCallApiGetUserById(request.getTeacherId());
            adClassCustomResponse.setUserNameTeacher(response.getUserName());
        }
        return adClassCustomResponse;
    }

    @Override
    public PageableObject<AdListClassCustomResponse> searchClass(final AdFindClassRequest adFindClass) {
        Pageable pageable = PageRequest.of(adFindClass.getPage() - 1, adFindClass.getSize());
        Page<AdClassResponse> pageList = repository.findClassBySemesterAndActivity(adFindClass, pageable);
        List<AdClassResponse> listResponse = pageList.getContent();
        List<String> idList = listResponse.stream()
                .map(AdClassResponse::getTeacherId)
                .collect(Collectors.toList());

        List<SimpleResponse> response = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idList);
        List<AdListClassCustomResponse> listClassCustomResponses = new ArrayList<>();
        for (AdClassResponse adClassResponse : listResponse) {
            AdListClassCustomResponse adListClassCustomResponse = new AdListClassCustomResponse();
            adListClassCustomResponse.setId(adClassResponse.getId());
            adListClassCustomResponse.setClassSize(adClassResponse.getClassSize());
            adListClassCustomResponse.setClassPeriod(adClassResponse.getClassPeriod());
            adListClassCustomResponse.setStartTime(adClassResponse.getStartTime());
            adListClassCustomResponse.setCode(adClassResponse.getCode());
            adListClassCustomResponse.setTeacherId(adClassResponse.getTeacherId());
            adListClassCustomResponse.setStt(adClassResponse.getStt());
            adListClassCustomResponse.setNameLevel(adClassResponse.getNameLevel());
            adListClassCustomResponse.setActivityName(adClassResponse.getActivityName());
            for (SimpleResponse simpleResponse : response) {
                if (adClassResponse.getTeacherId().equals(simpleResponse.getId())) {
                    adListClassCustomResponse.setUserNameTeacher(simpleResponse.getUserName());
                    break;
                }
            }

            listClassCustomResponses.add(adListClassCustomResponse);
        }
        PageableObject<AdListClassCustomResponse> pageableObject = new PageableObject<>();
        pageableObject.setData(listClassCustomResponses);
        pageableObject.setCurrentPage(pageList.getNumber());
        pageableObject.setTotalPages(pageList.getTotalPages());
        return pageableObject;
    }

    @Override
    public AdDetailClassRespone adFindClassById(String id) {
        Optional<AdDetailClassRespone> adDetailClassRespone = repository.adfindClassById(id);
        if (!adDetailClassRespone.isPresent()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        return adDetailClassRespone.get();
    }
}
