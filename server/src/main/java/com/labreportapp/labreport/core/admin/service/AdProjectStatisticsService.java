package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.AdFindProjectStatisticsRequest;
import com.labreportapp.labreport.core.admin.model.response.AdObjListStatisticsResponse;

/**
 * @author hieundph25894-duchieu212
 */
public interface AdProjectStatisticsService {

    AdObjListStatisticsResponse findAllProjectStatisticTypeXuong(final AdFindProjectStatisticsRequest request);
}
