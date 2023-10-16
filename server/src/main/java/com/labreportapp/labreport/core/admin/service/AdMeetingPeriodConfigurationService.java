package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.*;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingPeriodConfigurationResponse;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.entity.MeetingPeriod;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface AdMeetingPeriodConfigurationService {
    List<MeetingPeriod> findAllMeetingPeriod(Pageable pageable);

    MeetingPeriod createMeetingPeriod(@Valid final AdCreateMeetingPeriodRequest obj);

    MeetingPeriod updateMeetingPeriod(final AdUpdateMeetingPeriodRequest obj);

    PageableObject<AdMeetingPeriodConfigurationResponse> searchMeetingPeriod(final AdFindMeetingConfigurationRequest rep);

    Boolean deleteMeetingPeriod(final String id);
}
