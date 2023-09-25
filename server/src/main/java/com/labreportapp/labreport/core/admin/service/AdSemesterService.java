package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.AdCreateSemesterRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindSemesterRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateSemesterRequest;
import com.labreportapp.labreport.core.admin.model.response.AdSemesterResponse;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.entity.Semester;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdSemesterService {

    List<Semester> findAllSermester(Pageable pageable);

    Semester createSermester(@Valid AdCreateSemesterRequest obj);

    Semester updateSermester(@Valid AdUpdateSemesterRequest obj);

    PageableObject<AdSemesterResponse> searchSemester(final AdFindSemesterRequest rep);

    Semester findSemesterById(final String id);

    Boolean deleteSemester(final String id);

    Boolean updateStatusFeedback(String id);

}
