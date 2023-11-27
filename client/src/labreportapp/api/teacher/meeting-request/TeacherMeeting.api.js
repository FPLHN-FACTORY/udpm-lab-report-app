import { request } from "../../../helper/request.helper";
const url = `/teacher/meeting-request`;

export class TeacherMeetingRequestAPI {
  static getAllMeetingRequest(data) {
    return request({
      method: "GET",
      url:
        url +
        `?idClass=` +
        data.idClass +
        `&statusMeetingRequest=` +
        data.statusMeetingRequest +
        `&page=` +
        data.page +
        `&size=` +
        data.size,
    });
  }
  static sendMeetingRequestAgain(data) {
    return request({
      method: "PUT",
      url: url + `/sent-again`,
      data: data,
    });
  }
}
