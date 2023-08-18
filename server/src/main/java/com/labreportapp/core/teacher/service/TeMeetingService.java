package com.labreportapp.core.teacher.service;

import com.labreportapp.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.core.teacher.model.response.TeMeetingRespone;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeMeetingService {

    List<TeMeetingRespone> searchMeetingByIdClass(final TeFindMeetingRequest request);

}
