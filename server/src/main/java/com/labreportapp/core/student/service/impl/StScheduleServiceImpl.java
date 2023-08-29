package com.labreportapp.core.student.service.impl;

import com.labreportapp.core.admin.model.response.AdSemesterResponse;
import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.core.student.model.request.StFindScheduleRequest;
import com.labreportapp.core.student.model.response.StScheduleResponse;
import com.labreportapp.core.student.repository.StScheduleRepository;
import com.labreportapp.core.student.service.StScheduleService;
import com.labreportapp.entity.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StScheduleServiceImpl implements StScheduleService {
    @Autowired
    private StScheduleRepository stScheduleRepository;

    private List<StScheduleResponse> adScheduleResponseList;

    @Override
    public List<Meeting> findAllMeeting(Pageable pageable) {
        return stScheduleRepository.getAllMeeting(pageable);
    }

    @Override
    public PageableObject<StScheduleResponse> findScheduleByStudent(StFindScheduleRequest rep) {
        Pageable pageable = PageRequest.of(rep.getPage()-1, rep.getSize());
        Page<StScheduleResponse> stScheduleResponses = stScheduleRepository.findScheduleByStudent(rep, pageable);
        adScheduleResponseList = stScheduleResponses.stream().toList();
        return new PageableObject<>(stScheduleResponses);
    }
}
