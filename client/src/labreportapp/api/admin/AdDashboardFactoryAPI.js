import { request } from "../../helper/request.helper";

export class AdDashboardFactoryAPI {
  static loadData = (filter) => {
    return request({
      method: "GET",
      url: `/admin/dashboard-lab-report-app`,
      params: filter
    });
  };
}
