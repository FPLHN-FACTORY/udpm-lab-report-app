package com.labreportapp.core.admin.service;

import com.labreportapp.core.admin.model.request.AdCreateSemesterRequest;
import com.labreportapp.core.admin.model.request.AdFindSemesterRequest;
import com.labreportapp.core.admin.model.request.AdUpdateSemesterRequest;
import com.labreportapp.core.admin.model.response.AdSemesterResponse;
import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.entity.Semester;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdSemesterService {

    List<Semester> findAllSermester(Pageable pageable);

    Semester createSermester(@Valid final AdCreateSemesterRequest obj);

    Semester updateSermester(final AdUpdateSemesterRequest obj);

    PageableObject<AdSemesterResponse> searchSemester(final AdFindSemesterRequest rep);

    Semester findSemesterById(final String id);

    Boolean deleteSemester(final String id);

}
