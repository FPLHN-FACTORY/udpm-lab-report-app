import { request } from "../../helper/request.helper";

const url = `/admin/project-statistics`;

export class AdProjectStatisticsAPI {
  static getProjectAllDuAn = (data) => {
    return request({
      method: "GET",
      url: url + "?startTime=" + data.startTime + "&endTime=" + data.endTime,
    });
  };
}
