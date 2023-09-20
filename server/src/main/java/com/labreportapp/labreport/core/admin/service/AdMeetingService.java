package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.AdCreateMeetingRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateMeetingRequest;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingCustom;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingResponse;
import com.labreportapp.labreport.entity.Meeting;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface AdMeetingService {

    List<AdMeetingCustom> getAllMeetingByIdClass(String idClass);

    AdMeetingCustom create(@Valid AdCreateMeetingRequest request);

    AdMeetingCustom update(@Valid AdUpdateMeetingRequest request);

    String delete(String id);
}
