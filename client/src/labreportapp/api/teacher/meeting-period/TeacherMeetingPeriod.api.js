import { request } from "../../../helper/request.helper";
const url = `/teacher/meeting-period`;

export class TeacherMeetingPeriodAPI {
  static getPeriod() {
    return request({
      method: "GET",
      url: url,
    });
  }

  static getMeetingPeriod() {
    return request({
      method: "GET",
      url: url + "/get-all",
    });
  }

  static getAllTeacher() {
    return request({
      method: "GET",
      url: url + "/teacher",
    });
  }
}
