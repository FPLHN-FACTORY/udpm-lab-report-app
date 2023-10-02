import { request } from "../../helper/request.helper";

const url = `/student/schedule`;

export class StScheduleAPI {
  static fetchAllSchedule = (filter) => {
    return request({
      method: "GET",
      url:
        url +
        `/getAll` +
        `?searchTime=` +
        filter.searchTime +
        `&page=` +
        filter.page +
        `&size=` +
        filter.size,
    });
  };

  static getAllSchedule = () => {
    return request({
      method: "GET",
      url: url + `/page/1`,
    });
  };
}
