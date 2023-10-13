import { request } from "../../../helper/request.helper";
const url = `/teacher/meeting-period`;

export class TeacherMeetingPeriodAPI {
  static getPeriod() {
    return request({
      method: "GET",
      url: url,
    });
  }
}
