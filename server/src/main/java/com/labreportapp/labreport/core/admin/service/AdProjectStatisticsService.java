package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.AdFindProjectStatisticTopRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindProjectStatisticsRequest;
import com.labreportapp.labreport.core.admin.model.response.AdObjListStatisticsResponse;
import com.labreportapp.labreport.core.admin.model.response.AdProjectStatisticTopFiveResponse;

import java.util.List;

/**
 * @author hieundph25894-duchieu212
 */
public interface AdProjectStatisticsService {

    AdObjListStatisticsResponse findAllProjectStatisticTypeXuong(final AdFindProjectStatisticsRequest request);

    List<AdProjectStatisticTopFiveResponse> getProjectFindTop(final AdFindProjectStatisticTopRequest request);
}
