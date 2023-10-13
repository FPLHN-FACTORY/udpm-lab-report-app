import { request } from "../../helper/request.helper";

const url = `/student/meeting-period`;

export class StMeetingPeriodAPI {
  static getPeriod() {
    return request({
      method: "GET",
      url: url,
    });
  }
}
