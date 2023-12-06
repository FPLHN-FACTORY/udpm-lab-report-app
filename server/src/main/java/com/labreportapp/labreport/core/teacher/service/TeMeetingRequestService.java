package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.teacher.model.request.TeFindMeetingRequestRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeMeetingRequestAgainRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdateMeetingRequestRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingRequestCustomResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

/**
 * @author hieundph25894 - duchieu212
 */
public interface TeMeetingRequestService {

    Page<TeMeetingRequestCustomResponse> getAll(final TeFindMeetingRequestRequest request);

    TeMeetingRequestCustomResponse update(@Valid TeUpdateMeetingRequestRequest request);

    boolean sendMeetingRequestAgain(TeMeetingRequestAgainRequest request);

    String showReasons(String idClass);

}
