package com.labreportapp.core.admin.service.impl;

import com.labreportapp.core.admin.model.request.AdCreateClassRequest;
import com.labreportapp.core.admin.model.request.AdFindClassRequest;
import com.labreportapp.core.admin.model.response.AdActivityClassResponse;
import com.labreportapp.core.admin.model.response.AdClassResponse;
import com.labreportapp.core.admin.model.response.AdSemesterAcResponse;
import com.labreportapp.core.admin.repository.AdClassRepository;
import com.labreportapp.core.admin.service.AdClassService;
import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.entity.Activity;
import com.labreportapp.entity.Class;
import com.labreportapp.infrastructure.constant.ClassPeriod;
import com.labreportapp.util.FormUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * @author quynhncph26201
 */
@Service
@Validated
public class AdClassServiceImpl implements AdClassService {
    private FormUtils formUtils = new FormUtils();

    @Autowired
private AdClassRepository repository;
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
        return repository.findClassByCondition(code,classPeriod ,idTeacher);
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
    public Class createClass(@Valid AdCreateClassRequest request) {

        Class classs = formUtils.convertToObject(Class.class, request);






        return repository.save(classs);
    }

    @Override
    @CacheEvict(value = {"adClass"}, allEntries = true)
    public PageableObject<AdClassResponse> searchClass(final AdFindClassRequest adFindClass) {
        Pageable pageable = PageRequest.of(adFindClass.getPage() - 1, adFindClass.getSize());
        Page<AdClassResponse> pageList = repository.findClassBySemesterAndActivity(adFindClass, pageable);
        return new PageableObject<>(pageList);
    }


}
