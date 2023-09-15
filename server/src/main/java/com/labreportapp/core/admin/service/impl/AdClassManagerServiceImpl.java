package com.labreportapp.core.admin.service.impl;

import com.labreportapp.core.admin.model.request.AdCreateClassRequest;
import com.labreportapp.core.admin.model.request.AdFindClassRequest;
import com.labreportapp.core.admin.model.response.*;
import com.labreportapp.core.admin.repository.AdActivityRepository;
import com.labreportapp.core.admin.repository.AdClassRepository;
import com.labreportapp.core.admin.service.AdClassService;
import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.core.common.response.SimpleResponse;
import com.labreportapp.entity.Activity;
import com.labreportapp.entity.Class;
import com.labreportapp.infrastructure.apiconstant.ApiConstants;
import com.labreportapp.infrastructure.constant.ClassPeriod;
import com.labreportapp.infrastructure.constant.Message;
import com.labreportapp.infrastructure.exception.rest.RestApiException;
import com.labreportapp.util.ConvertListIdToString;
import com.labreportapp.util.FormUtils;
import com.labreportapp.util.RandomString;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    @Override
    public List<AdClassResponse> getAllClass() {
        return repository.getAllClass();
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
        if (!request.getTeacherId().equals("")) {
            adClassCustomResponse.setTeacherId(request.getTeacherId());
            String apiUrl = ApiConstants.API_LIST_TEACHER;

            ResponseEntity<SimpleResponse> responseEntity =
                    restTemplate.exchange(apiUrl + "/" + request.getTeacherId(), HttpMethod.GET, null,
                            new ParameterizedTypeReference<SimpleResponse>() {
                            });

            SimpleResponse response = responseEntity.getBody();
            adClassCustomResponse.setUserNameTeacher(response.getUsername());
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
        if (!request.getTeacherId().equals("")) {
            adClassCustomResponse.setTeacherId(request.getTeacherId());
            String apiUrl = ApiConstants.API_LIST_TEACHER;

            ResponseEntity<SimpleResponse> responseEntity =
                    restTemplate.exchange(apiUrl + "/" + request.getTeacherId(), HttpMethod.GET, null,
                            new ParameterizedTypeReference<SimpleResponse>() {
                            });

            SimpleResponse response = responseEntity.getBody();
            adClassCustomResponse.setUserNameTeacher(response.getUsername());
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
        String urlParams = ConvertListIdToString.convert(idList);
        String apiUrl = ApiConstants.API_LIST_TEACHER;

        ResponseEntity<List<SimpleResponse>> responseEntity =
                restTemplate.exchange(apiUrl + "?id=" + urlParams, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<SimpleResponse>>() {
                        });

        List<SimpleResponse> response = responseEntity.getBody();
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
            adListClassCustomResponse.setActivityName(adClassResponse.getActivityName());
            for (SimpleResponse simpleResponse : response) {
                if (adClassResponse.getTeacherId().equals(simpleResponse.getId())) {
                    adListClassCustomResponse.setUserNameTeacher(simpleResponse.getUsername());
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
