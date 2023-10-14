import { request } from "../../helper/request.helper";

export class AdMeetingPeriodAPI {
  static getAll = () => {
    return request({
      method: "GET",
      url: '/admin/meeting-period',
    });
  };
  
}