import { request } from "../../helper/request.helper";

const url = `/admin/meeting-period-configiration`;
export class AdMeetingPeriodConfigurationAPI {
  static fetchAllMeetingPeriodConfiguration = (filter) => {
    return request({
      method: "GET",
      url:
        url +
        `/search?name=` +
        filter.name +
        `&page=` +
        filter.page +
        `&size=` +
        filter.size,
    });
  };
  static addMeetingPeriod = (data) => {
    return request({
      method: "POST",
      url: url + `/add`,
      data: data,
    });
  };

  static updateMeetingPeriod = (data, id) => {
    return request({
      method: "PUT",
      url: url + `/update/` + id,
      data: data,
    });
  };

  static deleteMeetingPeriod = (id) => {
    return request({
      method: "DELETE",
      url: url + `/delete/` + id,
    });
  };

  static dowloadLog = () => {
    return request({
      method: "GET",
      url: url + `/download-log`,
      responseType: "blob",
    });
  };

  static showHistory = (params) => {
    return request({
      method: "GET",
      url: url + `/history`,
      params: params,
    });
  };
}
