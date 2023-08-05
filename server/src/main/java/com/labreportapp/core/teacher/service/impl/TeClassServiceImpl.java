package com.labreportapp.core.teacher.service.impl;

import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.core.common.base.PageableRequest;
import com.labreportapp.core.teacher.model.request.TeFindClass;
import com.labreportapp.core.teacher.model.response.TeClassResponse;
import com.labreportapp.core.teacher.repository.TeClassRepository;
import com.labreportapp.core.teacher.service.TeClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author hieundph25894
 */
@Service
public class TeClassServiceImpl implements TeClassService {

    @Autowired
    private TeClassRepository teClassRepository;

    @Override
    //@CacheEvict(value = {"classById"}, allEntries = true)
    public PageableObject<TeClassResponse> searchTeacherClass(TeFindClass teFindClass) {
        Pageable pageable = PageRequest.of(teFindClass.getPage() - 1, teFindClass.getSize());
        Page<TeClassResponse> pageList = teClassRepository.findClassBySemesterAndActivity(teFindClass, pageable);
        return new PageableObject<>(pageList);
    }
}
