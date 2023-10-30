import { request } from "../../helper/request.helper";

const url = `/admin/project-statistics`;

export class AdProjectStatisticsAPI {
  static featchProjectCount = (data) => {
    return request({
      method: "GET",
      url: url + `?startTime=` + data.name + `&endTime=` + data.page,
    });
  };
}
