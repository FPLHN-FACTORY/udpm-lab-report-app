package com.labreportapp.labreport.core.student.service;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.student.model.request.StFindScheduleRequest;
import com.labreportapp.labreport.core.student.model.response.StScheduleResponse;
import com.labreportapp.labreport.entity.Meeting;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StScheduleService {

    List<Meeting> findAllMeeting(Pageable pageable);

    PageableObject<StScheduleResponse> findScheduleByStudent(final StFindScheduleRequest rep);

}
