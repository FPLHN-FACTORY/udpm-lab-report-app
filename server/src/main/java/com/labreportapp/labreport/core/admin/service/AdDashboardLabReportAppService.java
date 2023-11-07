package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.AdDashboardLabReportAppRequest;
import com.labreportapp.labreport.core.admin.model.response.AdDashboardLabReportAppResponse;

/**
 * @author todo thangncph26123
 */
public interface AdDashboardLabReportAppService {

    AdDashboardLabReportAppResponse dashboard(final AdDashboardLabReportAppRequest request);
}
