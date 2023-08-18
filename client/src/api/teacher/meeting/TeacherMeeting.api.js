import { request } from "../../../helper/request.helper";
const url = `/teacher/meeting`;

export class TeacherMeetingAPI {
  static getAllMeetingByClass(idClass) {
    return request({
      method: "GET",
      url: url + `?idClass=` + idClass,
    });
  }
}
