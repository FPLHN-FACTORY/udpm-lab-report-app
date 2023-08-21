package com.labreportapp.core.teacher.service.impl;

import com.labreportapp.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.core.teacher.model.response.TeMeetingRespone;
import com.labreportapp.core.teacher.repository.TeMeetingRepository;
import com.labreportapp.core.teacher.service.TeMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hieundph25894
 */
@Service
public class TeMeetingServiceImpl implements TeMeetingService {

    @Autowired
    private TeMeetingRepository teMeetingRepository;

    @Override
    public List<TeMeetingRespone> searchMeetingByIdClass(TeFindMeetingRequest request) {
        List<TeMeetingRespone> list = teMeetingRepository.findMeetingByIdClass(request);
        return list;
    }

    @Override
    public Integer countMeetingByClassId(String idClass) {
        return teMeetingRepository.countMeetingByClassId(idClass);
    }

}
