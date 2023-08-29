package com.labreportapp.core.student.service;

import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.core.student.model.request.StFindScheduleRequest;
import com.labreportapp.core.student.model.response.StScheduleResponse;
import com.labreportapp.entity.Meeting;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StScheduleService {

    List<Meeting> findAllMeeting(Pageable pageable);

    PageableObject<StScheduleResponse> findScheduleByStudent(final StFindScheduleRequest rep);

}
