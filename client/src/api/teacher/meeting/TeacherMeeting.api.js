import { request } from "../../../helper/request.helper";
const url = `/teacher/meeting`;

export class TeacherMeetingAPI {
  static countMeetingByIdClass(idClass) {
    return request({
      method: "GET",
      url: url + `/count?idClass=` + idClass,
    });
  }
  static getAllMeetingByIdClass(idClass) {
    return request({
      method: "GET",
      url: url + `?idClass=` + idClass,
    });
  }
  static getDetailByIdMeeting(idMeeting) {
    return request({
      method: "GET",
      url: url`?idMeeting=` + idMeeting,
    });
  }
}
